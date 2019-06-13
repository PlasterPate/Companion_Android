package com.dalisyron.data.repository

import com.dalisyron.data.datasource.PlaceRemoteDataSource
import com.dalisyron.data.model.PlaceEntity

class PlaceRepository(private val placeRemoteDataSource : PlaceRemoteDataSource) {

    fun getAutoCompletePlaces(searchText : String) : List<PlaceEntity> {
        return placeRemoteDataSource.getAutoCompletePlaces(searchText)
    }

}