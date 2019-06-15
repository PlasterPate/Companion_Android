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
<<<<<<< HEAD
        fun setStartTripBtnState(state : Boolean)
        fun showCurvedPolyline(src : LatLng, dest : LatLng, curve : Double)
        fun removeCurvedPolyline()
    }

    interface Presenter {
        fun onPinLocked(source : LatLng, destination : LatLng)
        fun onDestinationCancled(destination: LatLng)
=======
        fun disableStartTripBtn()
        fun showCurvedPolyline(src : LatLng, dest : LatLng, curve : Double, googleMap: GoogleMap)
        fun zoomPlace()
    }

    interface Presenter {
        fun onPinLocked(source : LatLng, destination : LatLng, googleMap: GoogleMap)
        fun onReturnFromSearch(searchItemLocation : LatLng?)
>>>>>>> 5f48a2a2fe84779648880e5ebd4b8b37798995af
    }
}