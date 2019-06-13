package com.dalisyron.data.datasource

import com.dalisyron.data.model.PlaceEntity
import io.reactivex.Single

interface PlaceRemoteDataSource {

    fun getAutoCompletePlaces(searchText : String) : List<PlaceEntity>
}