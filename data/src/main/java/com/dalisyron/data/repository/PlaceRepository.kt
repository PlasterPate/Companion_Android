package com.dalisyron.data.repository

import com.dalisyron.data.datasource.PlaceRemoteDataSource
import javax.inject.Inject


class PlaceRepository @Inject constructor(private val placeRemoteDataSource: PlaceRemoteDataSource) {

    fun getAutoCompletePlaces(input : String, key : String) : Single<>{
        return
    }
}