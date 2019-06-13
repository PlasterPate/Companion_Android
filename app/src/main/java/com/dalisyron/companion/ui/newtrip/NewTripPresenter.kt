package com.dalisyron.companion.ui.newtrip

import android.location.Location
import com.dalisyron.data.repository.UserRepository
import com.google.android.gms.maps.model.LatLng

class NewTripPresenter : NewTripContract.Presenter {

    lateinit var view : NewTripContract.View

    override fun onPinLocked(source : LatLng, destination : LatLng) {
        val average = LatLng((source.latitude + destination.latitude) / 2, (source.longitude + destination.longitude) / 2)
        view.moveCamera(average)
        view.zoomOutMap()
    }
}