package com.dalisyron.companion.ui.newtrip

import android.location.Location
import com.dalisyron.data.repository.UserRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class NewTripPresenter : NewTripContract.Presenter {

    override fun onReturnFromSearch(searchItemLocation: LatLng?) {
        println("In presenter with $searchItemLocation")
        searchItemLocation?.let {
            view.moveCamera(it)
            view.zoomPlace()
        }
    }

    lateinit var view : NewTripContract.View

    override fun onPinLocked(source : LatLng, destination : LatLng, googleMap: GoogleMap) {
        val average = LatLng((source.latitude + destination.latitude) / 2, (source.longitude + destination.longitude) / 2)
        view.moveCamera(average)
        view.zoomOutMap()
        view.makePinInvisible()
        view.disableStartTripBtn()
        view.showCurvedPolyline(source, destination, .25, googleMap)
    }

}