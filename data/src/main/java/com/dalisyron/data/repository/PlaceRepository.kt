package com.dalisyron.data.repository

import com.dalisyron.data.datasource.PlaceRemoteDataSource
import com.dalisyron.data.model.PlaceEntity
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


class PlaceRepository @Inject constructor(private val placeRemoteDataSource: PlaceRemoteDataSource) {

    fun getPlaceDetails(placeId: String, key: String): Single<PlaceEntity> {
        return placeRemoteDataSource.getPlaceDetails(placeId, key)
    }

    fun getAutoCompletePlacesIds(input: String, key: String): Single<List<String>> {
        return placeRemoteDataSource.getAutoCompletePlacesIds(input, key)
    }

    fun getPlaces(input: String): Observable<PlaceEntity> {
        return placeRemoteDataSource.getPlaces(input)
    }
}