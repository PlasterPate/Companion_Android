package com.dalisyron.companion.ui.newtrip

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dalisyron.companion.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import kotlinx.android.synthetic.main.fragment_new_trip.*
import android.content.Context.LOCATION_SERVICE
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.LocationListener
import android.location.LocationProvider
import android.opengl.Visibility
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.dalisyron.companion.ui.search.SearchFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*


class NewTripFragment : Fragment(), NewTripContract.View {

    override fun zoomPlace() {
        mapView.getMapAsync { googleMap ->
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
        }
    }

    var searchItemLocation : LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchItemLocation = arguments?.getParcelable("latlng")
    }
    override fun moveCamera(position : LatLng) {
        mapView.getMapAsync { googleMap ->
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        }
    }

    override fun zoomOutMap() {
        mapView.getMapAsync { googleMap ->
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }

    override fun makePinInvisible() {
        pin.visibility = View.INVISIBLE
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

    override fun disableStartTripBtn() {
        start_trip_button.isEnabled = true
    }

    override fun showCurvedPolyline(src: LatLng, dest: LatLng, curve: Double, googleMap: GoogleMap) {
        val distance = SphericalUtil.computeDistanceBetween(src, dest)
        val heading = SphericalUtil.computeHeading(src, dest)

    //Midpoint position
        val midPoint = SphericalUtil.computeOffset(src, distance*0.5, heading)

        //Apply some mathematics to calculate position of the circle center
        val x : Double = (1-curve*curve)*distance*0.5/(2*curve)
        val r : Double = (1+curve*curve)*distance*0.5/(2*curve)

        val c = SphericalUtil.computeOffset(midPoint, x, heading + 90.0)

        //Polyline options
        val options = PolylineOptions()

        //Calculate heading between circle center and two points
        val h1 : Double = SphericalUtil.computeHeading(c, src)
        val h2 : Double = SphericalUtil.computeHeading(c, dest)

        //Calculate positions of points on circle border and add them to polyline options
        val numpoints = 1000
        val step = (h2 -h1) / numpoints

        for (i in 0..numpoints)
            options.add(SphericalUtil.computeOffset(c, r, h1 + i * step))

        //googleMap.addPolyline(PolylineOptions().width())
        googleMap.addPolyline(options
            .width(5f)
            .color(Color.BLACK)
            .geodesic(false))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_trip, container, false)
    }


    private val presenter: NewTripPresenter by lazy {
        NewTripPresenter().apply {
            view = this@NewTripFragment
        }
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)


        searchViewNewTrip.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.content_frame, SearchFragment())
                ?.commit()
        }

        searchEditText.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.content_frame, SearchFragment())
                ?.commit()
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

                presenter.onPinLocked(sourceLocation, destinationLocation, googleMap)
                googleMap.addMarker(MarkerOptions()
                    .position(destinationLocation)
                    .title("مقصد")
                    .icon(markerIcon))
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

        presenter.onReturnFromSearch(searchItemLocation)

    }

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

            val munich = LatLng(48.1351, 11.5820)
            val cameraPosition = CameraPosition.Builder().target(munich).zoom(15f).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
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

        fun newInstance(latLng: LatLng) : NewTripFragment {
            return NewTripFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("latlng", latLng)
                }
            }
        }
    }
}