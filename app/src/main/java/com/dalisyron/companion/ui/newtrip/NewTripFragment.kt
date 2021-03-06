package com.dalisyron.companion.ui.newtrip



import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.dalisyron.companion.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import kotlinx.android.synthetic.main.fragment_new_trip.*
import android.content.Context.LOCATION_SERVICE
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.ViewAnimationUtils
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.dalisyron.companion.ui.helpeeStatus.HelpeeStatusFragment
import com.dalisyron.companion.ui.search.SearchFragment
import com.dalisyron.companion.ui.addContacts.AddContactsFragment
import com.dalisyron.companion.ui.addContacts.AddContactsFragment.Companion.CONTACT_KEY
import com.dalisyron.data.model.ContactEntity
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import dagger.android.support.DaggerFragment
import java.lang.Math.abs
import javax.inject.Inject

class NewTripFragment : DaggerFragment(), NewTripContract.View {

    override fun showTripCreatedMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun getSource(): LatLng {
        return sourceLocation
    }

    override fun getDestination(): LatLng {
        return destinationLocation
    }

    var searchItemLocation : LatLng? = null
    var companionContact : ContactEntity? = null

    lateinit var sourceLocation : LatLng
    lateinit var destinationLocation : LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchItemLocation = arguments?.getParcelable(LATLNG_KEY)
        arguments?.getSerializable(CONTACT_KEY)?.let {
            companionContact = it as ContactEntity
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_trip, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anim = ViewAnimationUtils.createCircularReveal(view, this.arguments!!.getFloat("x").toInt(),
            this.arguments!!.getFloat("y").toInt(),0.toFloat(),2000.toFloat())
        anim.duration = 1200
        anim.start()

        presenter.view = this

        mapView.onCreate(savedInstanceState)

        searchEditText.setOnClickListener {
            presenter.onSearchBarClicked()
        }

        imageView.setOnClickListener {
            presenter.onSearchBarClicked()
        }

        start_trip_button.setOnClickListener {
            presenter.onNewTripClicked()
        }

        pin.setOnClickListener {
            mapView.getMapAsync { googleMap ->
                val destinationLocation = googleMap.projection.visibleRegion.latLngBounds.center

                val locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager

                val location = when {
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ->
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ->
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                    else -> locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                }

                val sourceLocation : LatLng = if(location != null)
                    LatLng(location.latitude, location.longitude)
                else
                    destinationLocation
                val markerIcon = vectorToBitmap(R.drawable.ic_map_pin)

                this.sourceLocation = sourceLocation
                this.destinationLocation = destinationLocation

                presenter.onPinLocked(sourceLocation, destinationLocation)
                googleMap.addMarker(MarkerOptions()
                    .position(destinationLocation)
                    .title("مقصد")
                    .icon(markerIcon))

                googleMap.setOnMarkerClickListener {
                    presenter.onDestinationCancled(destinationLocation)
                    false
                }
            }

        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
        } else {
            initMap()
        }
    }

    override fun zoomOutMap(source : LatLng, destination : LatLng) {
        mapView.getMapAsync { googleMap ->
            val latDist = abs(source.latitude - destination.latitude)
            val longDist = abs(source.longitude - destination.longitude)
            val latMid = (source.latitude + destination.latitude)*0.5
            val longMid = (source.longitude + destination.longitude)*0.5
            val builder = LatLngBounds.builder()
            val temp1 = LatLng(latMid + longDist*0.5, longMid - latDist*0.5)
            val temp2 = LatLng(latMid - longDist*0.5, longMid + latDist*0.5)
            builder.include(source)
            builder.include(destination)
            builder.include(temp1)
            builder.include(temp2)
            val bounds = builder.build()
            val padding = 100
            val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            googleMap.animateCamera(cu)
        }
    }

    override fun zoomInDestination(destination: LatLng) {
        mapView.getMapAsync { googleMap ->
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 15.0f))
        }
    }

    override fun pinVisibility(visibility: Boolean) {
        when(visibility){
            true -> pin.visibility = View.VISIBLE
            false -> pin.visibility = View.INVISIBLE
        }
    }

    override fun vectorToBitmap(drawableId: Int) : BitmapDescriptor{
        val drawable : Drawable? = ResourcesCompat.getDrawable(resources, drawableId, null)
        val bitmap : Bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(drawable, Color.BLACK)
        drawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun setStartTripBtnState(state : Boolean) {
        start_trip_button.isEnabled = state
    }

    override fun showCurvedPolyline(src: LatLng, dest: LatLng, curve: Double) {
//        //zoom first to draw a smoother line
//        mapView.getMapAsync{googleMap ->
//            googleMap.moveCamera(CameraUpdateFactory.zoomTo(21f))
//        }

        //Calculate distance and heading between two points
        val distance = SphericalUtil.computeDistanceBetween(src, dest)
        val heading = SphericalUtil.computeHeading(src, dest)

        //Midpoint position
        val midPoint = SphericalUtil.computeOffset(src, distance*0.5, heading)

        //Apply some mathematics to calculate position of the circle center
        val x : Double = (1-curve*curve)*distance*0.5/(2*curve)
        val r : Double = (1+curve*curve)*distance*0.5/(2*curve)

        val ninety = if (src.longitude > dest.longitude)
            -90
        else
            90

        val c = SphericalUtil.computeOffset(midPoint, x, heading + ninety)

        //Polyline options
        val options = PolylineOptions()

        //Calculate heading between circle center and two points
        val h1 : Double = SphericalUtil.computeHeading(c, src)
        val h2 : Double = SphericalUtil.computeHeading(c, dest)

        //Calculate positions of points on circle border and add them to polyline options
        val numPoints = 10000
        val step : Double = (h2 -h1) / numPoints


        for (i in 0..numPoints)
            options.add(SphericalUtil.computeOffset(c, r, h1 + i * step))

        mapView.getMapAsync { googleMap ->
            googleMap.addPolyline(options
                .width(5f)
                .color(Color.BLACK)
                .geodesic(false))
        }
    }

    override fun moveCamera(position : LatLng) {

        mapView.getMapAsync { googleMap ->
            val cameraPosition = CameraPosition.Builder().target(position).zoom(16f).build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun zoomPlace() {
        mapView.getMapAsync { googleMap ->
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        }
    }

    override fun removeCurvedPolyline() {
        mapView.getMapAsync { googleMap ->
            googleMap.clear()
        }
    }


    override fun navigateToSearchFragment() {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.content_frame, SearchFragment())
            ?.commit()
    }

    override fun navigateToHelpeeStatus() {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.content_frame, HelpeeStatusFragment())
            ?.addToBackStack("HelpeeStatusFromNewTrip")?.commit()
    }

//    private val presenter: NewTripPresenter by lazy {
//        NewTripPresenter().apply {
//            view = this@NewTripFragment
//        }
//    }
    @Inject
    lateinit var presenter : NewTripPresenter

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults.size > 0) {
                initMap()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun initMap() {

        Log.d("initMap", "init map")

        mapView.onResume()

        try {
            MapsInitializer.initialize(requireContext())
        } catch (_: Exception) {

        }

        val locationButton =
            (mapView.findViewById<View>(Integer.parseInt("1")).getParent() as View).findViewById<View>(
                Integer.parseInt(
                    "2"
                )
            )
        // and next place it, on bottom right (as Google Maps app)
        val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
        // position on right bottom
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        layoutParams.setMargins(0, 0, 30, 200)

        mapView.getMapAsync { googleMap ->
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true

            val tehran = LatLng(35.6892, 51.3890)

            if (searchItemLocation != null) {
                presenter.onReturnFromSearch(searchItemLocation)
            } else {
                moveCamera(tehran)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {

        const val LATLNG_KEY = "latlng"

        fun newInstance(contactEntity: ContactEntity) : NewTripFragment {
            return NewTripFragment().apply {
                val bundle = Bundle()
                bundle.putSerializable(AddContactsFragment.CONTACT_KEY, contactEntity)
                arguments = bundle
            }
        }

        fun newInstance(latLng: LatLng) : NewTripFragment {
            return NewTripFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LATLNG_KEY, latLng)
                }
            }
        }
        fun newInstance(x:Float,y:Float) : NewTripFragment{
            return NewTripFragment().apply {
                val bundle = Bundle()
                bundle.putFloat("x",x)
                bundle.putFloat("y",y)
                arguments = bundle
            }
        }
    }
}