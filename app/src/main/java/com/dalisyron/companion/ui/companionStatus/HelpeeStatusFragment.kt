package com.dalisyron.companion.ui.companionStatus

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dalisyron.companion.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.circularreveal.cardview.CircularRevealCardView
import kotlinx.android.synthetic.main.fragment_helpee_status.*
import kotlinx.android.synthetic.main.fragment_new_trip.mapView


class HelpeeStatusFragment : Fragment(), HelpeeStatusContract.view {



    private val presenter: HelpeeStatusPresenter by lazy {
        HelpeeStatusPresenter().apply {
            view = this@HelpeeStatusFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_helpee_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)


        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
        } else {
            initMap()
        }

        val fab = view.findViewById(R.id.companion_button) as FloatingActionButton

        val fabLocations = IntArray(2)
        val emergency = view.findViewById(R.id.emergency_button) as MaterialButton

        val asd = view.findViewById(R.id.companion_panel) as CircularRevealCardView
        val obj = ObjectAnimator.ofFloat(fab, "translationX", ((asd.left + asd.right) / 2).toFloat())
        val obj1 = ObjectAnimator.ofFloat(fab, "translationY", fabLocations[0].toFloat())

                val constraintSet1 = ConstraintSet()
                constraintSet1.clone(sceneRoot)
                val constraintSet2 = ConstraintSet()
                constraintSet2.clone(context, R.layout.fragment_helpee_status_reveal)
                var changed = false
        val anim = ViewAnimationUtils.createCircularReveal(asd,200,200,50.toFloat(),0.toFloat())

        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                TransitionManager.beginDelayedTransition(sceneRoot)
                val constraint = if (changed) constraintSet1 else constraintSet2
                constraint.applyTo(sceneRoot)
                changed = !changed
                if(changed) {
                    asd.visibility = View.VISIBLE
                }
                else
                    asd.visibility = View.INVISIBLE
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults.isNotEmpty()) {
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

            val myLocation = myLocation()
            val cameraPosition = CameraPosition.Builder().target(myLocation).zoom(15f).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    @SuppressLint("MissingPermission")
    fun myLocation(): LatLng {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location = when {
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ->
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ->
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            else -> locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        }

        return if (location != null)
            LatLng(location.latitude, location.longitude)
        else
            LatLng(35.6892, 51.3890)
    }
}