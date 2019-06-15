package com.dalisyron.companion.ui.newtrip

import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

interface NewTripContract {

    interface View {
        fun zoomOutMap(source : LatLng, destination : LatLng)
        fun zoomInDestination(destination: LatLng)
        fun pinVisibility(visibility : Boolean)
        fun vectorToBitmap(drawableId : Int) : BitmapDescriptor
        fun setStartTripBtnState(state : Boolean)
        fun showCurvedPolyline(src : LatLng, dest : LatLng, curve : Double)
        fun removeCurvedPolyline()
    }

    interface Presenter {
        fun onPinLocked(source : LatLng, destination : LatLng)
        fun onDestinationCancled(destination: LatLng)
    }
}