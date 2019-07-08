package com.dalisyron.companion.ui.companionStatus

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_ping.*
import android.os.CountDownTimer
import android.util.Log
import android.view.MotionEvent
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.dalisyron.companion.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_new_trip.*
import kotlinx.android.synthetic.main.fragment_ping.mapView


class CompanionStatusFragment : Fragment(), CompanionStatusContract.View {
    lateinit var mCountDownTimer: CountDownTimer
    lateinit var mapView: MapView

    override fun cancleProgressBar() {
        mCountDownTimer.cancel()
    }

    override fun setProgressBarVisibility(visibility: Int) {
        progress_bar.visibility = visibility
    }

    override fun showProgressBar() {
        val mProgressBar: ProgressBar = progress_bar
        mProgressBar.progress = 0
        val givenTime : Long = 15000
        mCountDownTimer = object : CountDownTimer(givenTime, givenTime / progress_bar.max) {

            override fun onTick(millisUntilFinished: Long) {
                mProgressBar.incrementProgressBy(1)
            }

            override fun onFinish() {
                //Do what you want
                mProgressBar.progress = progress_bar.max
                cancel()
                presenter.onProgressFinished()
            }
        }
        mCountDownTimer.start()
    }

    override fun setPingButtonState(state: Boolean) {
        ping_button.isEnabled = state
        val color = if(state){
            R.color.ripple_color
        }else{
            R.color.black_theme2
        }
        ping_button.background.setColorFilter(resources.getColor(color), PorterDuff.Mode.MULTIPLY)
    }

    override fun showDialogBox(title: String, message: String, buttonText: String) {
        val dialogBuilder = AlertDialog.Builder(this.context)

        dialogBuilder.setMessage(message)
            .setPositiveButton(buttonText, DialogInterface.OnClickListener{dialog, which ->  })

        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }

    private val presenter: CompanionStatusPresenter by lazy {
        CompanionStatusPresenter().apply {
            view = this@CompanionStatusFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ping, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        initMap()

        ping_button.setOnClickListener{
            presenter.onPingButtonClicked()
        }
    }

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