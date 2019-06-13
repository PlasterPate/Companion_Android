package com.dalisyron.remote.api

import com.dalisyron.remote.dto.place.PlaceAutoCompeleteResponseDto
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface PlaceService {

    @GET("autocomplete/json")
    fun getAutoCompletePlaces(@Query("input") input : String, @Query("key") key : String) : Single<PlaceAutoCompeleteResponseDto>

    companion object {
        const val API_KEY = "AIzaSyCpM6sa61XxyPPiC7Lf32dcXaK0TiIoTr4"
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

    service.getAutoCompletePlaces("tehran iust", PlaceService.API_KEY).subscribe( {placeAutoCompeleteResponseDto ->
        println(placeAutoCompeleteResponseDto)}, {it -> println(it)}
    )
}