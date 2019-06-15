package com.dalisyron.remote.datasource

import com.dalisyron.data.datasource.PlaceRemoteDataSource
import com.dalisyron.data.model.PlaceEntity
import com.dalisyron.remote.api.PlaceService
import com.dalisyron.remote.dto.place.autocomplete.PlaceAutoCompleteResponseDto
import com.dalisyron.remote.dto.place.detail.PlaceDetailsResponseDto
import com.dalisyron.remote.mappers.toPlaceEntity
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class PlaceRemoteDataSourceImpl @Inject constructor(private val placeService: PlaceService) : PlaceRemoteDataSource {
    override fun getPlaceDetails(placeId: String, key: String): Single<PlaceEntity> {
        return placeService.getPlaceDetails(placeId, key).map { placeDetailsResponseDto ->
            placeDetailsResponseDto.result.toPlaceEntity()
        }
    }

    override fun getAutoCompletePlacesIds(input: String, key: String): Single<List<String>> {
        return placeService.getAutoCompletePlaces(input, key).map { placeAutoCompeleteResponseDto ->
            placeAutoCompeleteResponseDto.items.map { placeAutoCompleteDto ->
                placeAutoCompleteDto.placeId
            }
        }
    }

    override fun getPlaces(input: String): Observable<PlaceEntity> {
        return placeService.getAutoCompletePlaces(input, PlaceService.API_KEY)
            .map { placeAutoCompleteResponseDto ->
                placeAutoCompleteResponseDto.items.map {
                    it -> it.placeId
                }
            }.flatMapObservable {
                it -> Observable.fromIterable(it)
            }.flatMap {
                it -> placeService.getPlaceDetails(it, PlaceService.API_KEY).toObservable()
            }.map {placeDetailsResponseDto ->
                placeDetailsResponseDto.result.toPlaceEntity()
            }
    }

}