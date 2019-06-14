package com.dalisyron.companion.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dalisyron.companion.R
import com.dalisyron.data.model.PlaceEntity
import com.dalisyron.data.repository.PlaceRepository
import com.dalisyron.remote.api.PlaceService
import com.dalisyron.remote.datasource.PlaceRemoteDataSourceImpl
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginFragment : DaggerFragment() {


    @Inject
    lateinit var placeRepository: PlaceRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeRepository.getAutoCompletePlacesIds("Tehran", PlaceService.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({placeIds -> onPlaceIdsReady(placeIds)}, {it -> println(it)})
    }

    fun onPlaceIdsReady(ids : List<String>) {
        ids.forEach {id ->
            placeRepository.getPlaceDetails(id, PlaceService.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ place ->
                    println(place)
                }, { place ->
                    println("inner error -> $place")
                })
        }
    }

}