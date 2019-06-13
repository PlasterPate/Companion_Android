package com.dalisyron.remote.datasource

import com.dalisyron.data.datasource.PlaceRemoteDataSource
import com.dalisyron.data.model.PlaceEntity
import com.dalisyron.remote.api.PlaceService
import io.reactivex.Single

class PlaceRemoteDataSourceImpl(private val placeService: PlaceService) : PlaceRemoteDataSource {
    override fun getAutoCompletePlaces(searchText: String): List<PlaceEntity> {
        return placeService.getAutoCompletePlaces(searchText)
    }

}