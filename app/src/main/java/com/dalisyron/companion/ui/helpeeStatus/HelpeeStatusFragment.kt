package com.dalisyron.companion.ui.helpeeStatus

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dalisyron.companion.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_helpee_status.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.*
import com.dalisyron.data.model.CompanionEntity
import com.google.android.gms.maps.MapView
import com.google.android.material.circularreveal.cardview.CircularRevealCardView
import com.squareup.picasso.Picasso


class HelpeeStatusFragment : Fragment(), HelpeeStatusContract.view {

    override fun showCompanionInfo(companionEntity: CompanionEntity) {
        companion_name.text = companionEntity.name
        companion_phone_number.text = companionEntity.phoneNumber
        Picasso.get().load(companionEntity.imageSource).into(companion_button)
    }

    override fun hidePingProgress() {
        countDownTimer.cancel()
        dialog.hide()
    }

    lateinit var countDownTimer: CountDownTimer
    lateinit var dialog: Dialog
    lateinit var mapView: MapView

    override fun showPingProgress() {
        dialog = Dialog(this.context)
        dialog.setContentView(R.layout.dialog_ping)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
        val progress: ProgressBar = dialog.findViewById(R.id.circular_progressbar)
        val givenTime: Long = 15000
        progress.progress = 0

        countDownTimer = object : CountDownTimer(givenTime, givenTime / progress.max) {

            override fun onTick(millisUntilFinished: Long) {
                progress.incrementProgressBy(1)
            }

            override fun onFinish() {
                progress.progress = progress.max
                cancel()
                dialog.hide()
                presenter.onHelpeeNotRespond()
            }
        }
        countDownTimer.start()

        val pingRespondButton: FloatingActionButton = dialog.findViewById(R.id.ping_respond_button)
        pingRespondButton.setOnClickListener {
            presenter.onHelpeeRespond()
        }
        dialog.setCanceledOnTouchOutside(false)
    }

    override fun toastMessage(message: String) {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }


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
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        val anim = ViewAnimationUtils.createCircularReveal(view, 0.toInt(),
            0.toInt(),0.toFloat(),2000.toFloat())
        anim.duration = 1000
        anim.start()

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 123)
        } else {
            initMap()
        }

        emergency_button.setOnClickListener {
            presenter.onEmergencyButtonClicked()
        }

        val fab = view.findViewById(R.id.companion_button) as ImageButton

        val asd = view.findViewById(R.id.companion_panel) as CircularRevealCardView

        val constraintSet1 = ConstraintSet()
        constraintSet1.clone(sceneRoot)
        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(context, R.layout.fragment_helpee_status_reveal)
        var changed = false

        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                TransitionManager.beginDelayedTransition(sceneRoot)
                val constraint = if (changed) constraintSet1 else constraintSet2
                constraint.applyTo(sceneRoot)
                changed = !changed
                if (changed) {
                    asd.visibility = View.VISIBLE
                } else {
                    asd.visibility = View.INVISIBLE
                }
            }
        })

        val companionEntity = CompanionEntity(
            name = "Mohammad",
            phoneNumber = "09334537343",
            imageSource = "http://s8.picofile.com/file/8366209684/Mamad_pro.png"
        )
        presenter.onViewCreated(companionEntity)
    }

    private fun getCircularAnimator(targetView: View, sourceX: Int, sourceY: Int, speed: Long): Animator {
        val finalRadius = Math.hypot(targetView.width.toDouble(), targetView.height.toDouble()).toFloat()
        return ViewAnimationUtils.createCircularReveal(targetView, sourceX, sourceY, 0f, finalRadius).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = speed
        }
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