package com.dalisyron.companion.ui.search

import android.util.Log
import com.dalisyron.data.model.PlaceEntity
import com.dalisyron.data.repository.PlaceRepository
import com.dalisyron.remote.api.PlaceService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val placeRepository: PlaceRepository) : SearchContract.Presenter {

    val disposables: CompositeDisposable = CompositeDisposable()

    lateinit var view: SearchContract.View

    var itemList = ArrayList<PlaceEntity>()

    override fun onSearchSubmit(query: String) {
        view.showProgressBar()
        view.clearPlaces()
        placeRepository.getAutoCompletePlacesIds(query, PlaceService.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { ids ->
                    view.hideProgressBar()
                    ids.forEach { id ->
                        placeRepository.getPlaceDetails(id, PlaceService.API_KEY)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                { placeEntity ->
                                    view.addPlace(placeEntity)
                                },
                                {
                                    error -> println("Inner error ${error.message}")
                                }
                            )
                    }
                }, { it -> println("Error $it") }
            ).also {
                disposables.add(it)
            }
        /*
        placeRepository.getPlaces(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                view.showPlaces(itemList)
                println("places $itemList")
                view.hideProgressBar()
                itemList.clear()
            }
            .subscribe(
                {
                    itemList.add(it)
                }, {
                    Log.e("Error", it.message)
                }
            ).also {
                disposables.add(it)
            }
        */
    }

    override fun onSearchQueryChanged(query: String) {

    }

    override fun onPlaceItemClicked() {
    }

    fun onDestroyView() {
        disposables.dispose()
    }
}