package com.dalisyron.remote.api

import com.dalisyron.remote.dto.place.autocomplete.PlaceAutoCompleteResponseDto
import com.dalisyron.remote.dto.place.detail.PlaceDetailsResponseDto
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface PlaceService {

    @GET("autocomplete/json")
    fun getAutoCompletePlaces(@Query("input") input : String, @Query("key") key : String, @Query("location") location : String = "35.705876,51.393622", @Query("radius") radius : Int = 10000) : Single<PlaceAutoCompleteResponseDto>

    @GET("details/json")
    fun getPlaceDetails(@Query("placeid") placeId : String, @Query("key") key : String, @Query("fields") fields : String = "name,geometry") : Single<PlaceDetailsResponseDto>

    companion object {
        const val API_KEY = "AIzaSyDbqgzzskz921q3YJUPUUy0WIPgxLR4fIM"
        const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"

        fun create() : PlaceService {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PlaceService.BASE_URL)
                .build()
                .create(PlaceService::class.java)
        }
    }
}

fun main() {
    val service : PlaceService = PlaceService.create()

}