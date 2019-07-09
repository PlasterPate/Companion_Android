package com.dalisyron.companion.ui.companionStatus

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_companion_status.*
import android.os.CountDownTimer
import android.util.Log
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.dalisyron.companion.R
import com.dalisyron.companion.ui.newtrip.NewTripFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_companion_status.mapView


class CompanionStatusFragment : Fragment(), CompanionStatusContract.View {
    override fun showHelpeeSource(source : LatLng) {
        mapView.getMapAsync{googleMap ->
            googleMap.addMarker(MarkerOptions()
                .position(source)
                .icon(vectorToBitmap(R.drawable.ic_map_pin_lemon)))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(source, 16.0f))
        }
    }

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
        return inflater.inflate(R.layout.fragment_companion_status, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        initMap()

        presenter.onViewCreated()

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

    fun vectorToBitmap(drawableId: Int) : BitmapDescriptor {
        val drawable : Drawable? = ResourcesCompat.getDrawable(resources, drawableId, null)
        val bitmap : Bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(drawable, resources.getColor(R.color.ripple_color))
        drawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
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