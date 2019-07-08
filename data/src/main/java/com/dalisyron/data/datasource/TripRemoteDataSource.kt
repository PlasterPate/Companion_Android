package com.dalisyron.data.datasource

import com.dalisyron.data.model.TripItemEntity
import com.dalisyron.data.model.TripResponseEntity
import io.reactivex.Single

interface TripRemoteDataSource {

    fun createNewTrip(tripItemEntity : TripItemEntity, bearerToken : String) : Single<TripResponseEntity>

}