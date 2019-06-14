package com.dalisyron.companion.ui.newtrip

import android.location.Location
import com.dalisyron.data.repository.UserRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class NewTripPresenter : NewTripContract.Presenter {

    lateinit var view : NewTripContract.View

    override fun onPinLocked(source : LatLng, destination : LatLng) {
        view.showCurvedPolyline(source, destination, .5)
        view.zoomOutMap(source, destination)
        view.pinVisibility(false)
        view.setStartTripBtnState(false)
    }

    override fun onDestinationCancled(destination: LatLng) {
        view.removeCurvedPolyline()
        view.zoomInDestination(destination)
        view.pinVisibility(true)
        view.setStartTripBtnState(true)
    }
}