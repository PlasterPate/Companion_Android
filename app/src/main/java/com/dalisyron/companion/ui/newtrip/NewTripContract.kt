package com.dalisyron.companion.ui.newtrip

import android.location.Location
import com.google.android.gms.maps.model.LatLng

interface NewTripContract {

    interface View {
        fun zoomOutMap()
        fun moveCamera(position : LatLng)
    }

    interface Presenter {
        fun onPinLocked(source : LatLng, destination : LatLng)
    }
}