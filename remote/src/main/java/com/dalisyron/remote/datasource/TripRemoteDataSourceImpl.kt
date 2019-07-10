package com.dalisyron.remote.datasource

import com.dalisyron.data.datasource.TripRemoteDataSource
import com.dalisyron.data.model.TripItemEntity
import com.dalisyron.data.model.TripResponseEntity
import com.dalisyron.remote.api.TripService
import com.dalisyron.remote.dto.trip.TripResponseDto
import com.dalisyron.remote.mappers.toTripItemDto
import com.dalisyron.remote.mappers.toTripResponseEntity
import io.reactivex.Single
import javax.inject.Inject

class TripRemoteDataSourceImpl @Inject constructor(private val tripService : TripService) : TripRemoteDataSource {
    override fun createNewTrip(tripItemEntity: TripItemEntity, bearerToken : String): Single<TripResponseEntity> {
        return tripService.createNewTrip(tripItemEntity.toTripItemDto(), "Bearer " + bearerToken).map {tripResponseDto ->
            tripResponseDto.toTripResponseEntity()
        }
    }

}