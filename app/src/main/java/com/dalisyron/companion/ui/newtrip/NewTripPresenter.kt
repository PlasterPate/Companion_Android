package com.dalisyron.companion.ui.newtrip

import android.location.Location
import com.dalisyron.data.model.LatLngEntity
import com.dalisyron.data.model.TripItemEntity
import com.dalisyron.data.repository.TripRepository
import com.dalisyron.data.repository.UserRepository
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewTripPresenter @Inject constructor(private val tripRepository: TripRepository,
                                           private val userRepository: UserRepository) : NewTripContract.Presenter {

    lateinit var view : NewTripContract.View

    override fun onNewTripClicked() {
        userRepository.getUser().flatMap {id ->
            val source : LatLngEntity = with(view.getSource()){ LatLngEntity(this.latitude, this.longitude) }
            val destination : LatLngEntity = with(view.getDestination()){ LatLngEntity(this.latitude, this.longitude)}
            val tripItemEntity = TripItemEntity(
                helpjooId = id,
                source = source,
                destination = destination
            )
            tripRepository.createNewTrip(tripItemEntity)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({tripResponseEntity ->
                view.showTripCreatedMessage(tripResponseEntity.status)
            }, {it -> view.showTripCreatedMessage("Error ${it.message}")})
    }

    override fun onReturnFromSearch(searchItemLocation: LatLng?) {
        println("In presenter with $searchItemLocation")
        searchItemLocation?.let {
            view.moveCamera(it)
            view.zoomPlace()
            view.enableMyLocation()
        }
    }

    override fun onPinLocked(source : LatLng, destination : LatLng) {
        view.showCurvedPolyline(source, destination, .2)
        view.zoomOutMap(source, destination)
        view.pinVisibility(false)
        view.setStartTripBtnState(true)
    }

    override fun onSearchBarClicked() {
        view.navigateToSearchFragment()
    }

    override fun onDestinationCancled(destination: LatLng) {
        view.removeCurvedPolyline()
        view.zoomInDestination(destination)
        view.pinVisibility(true)
        view.setStartTripBtnState(false)
    }


}