package com.dalisyron.data.datasource

import com.dalisyron.data.model.PlaceEntity
import io.reactivex.Observable
import io.reactivex.Single

interface PlaceRemoteDataSource {

    fun getAutoCompletePlacesIds(input : String, key : String) : Single<List<String>>

    fun getPlaceDetails(placeId : String, key : String) : Single<PlaceEntity>

    fun getPlaces(input : String) : Observable<PlaceEntity>
}