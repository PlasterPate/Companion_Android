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
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_home.*


class NewTripFragment : Fragment(), NewTripContract.View {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_trip, container, false)
    }


    val presenter: NewTripPresenter by lazy {
        NewTripPresenter().apply {
            view = this@NewTripFragment
        }
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)

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

                presenter.onPinLocked(sourceLocation, destinationLocation)
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
}