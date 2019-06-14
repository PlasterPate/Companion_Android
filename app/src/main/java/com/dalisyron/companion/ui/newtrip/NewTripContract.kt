package com.dalisyron.companion.ui.newtrip

import android.location.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

interface NewTripContract {

    interface View {
        fun zoomOutMap()
        fun moveCamera(position : LatLng)
        fun makePinInvisible()
        fun vectorToBitmap(drawableId : Int) : BitmapDescriptor
        fun disableStartTripBtn()
        fun showCurvedPolyline(src : LatLng, dest : LatLng, curve : Double, googleMap: GoogleMap)
        fun zoomPlace()
    }

    interface Presenter {
        fun onPinLocked(source : LatLng, destination : LatLng, googleMap: GoogleMap)
        fun onReturnFromSearch(searchItemLocation : LatLng?)
    }
}