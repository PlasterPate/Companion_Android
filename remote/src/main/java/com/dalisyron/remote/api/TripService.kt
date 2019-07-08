package com.dalisyron.remote.api

import com.dalisyron.remote.api.TripService.Companion.BASE_URL
import com.dalisyron.remote.dto.trip.LatLngDto
import com.dalisyron.remote.dto.trip.TripItemDto
import com.dalisyron.remote.dto.trip.TripResponseDto
import com.dalisyron.remote.dto.user.UserIdDto
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TripService {

    @POST("/api/trips/")
    fun createNewTrip(@Body tripItemDto: TripItemDto, @Header("Authorization") bearerToken: String): Single<TripResponseDto>

    companion object {
        const val BASE_URL: String = "http://194.225.229.210:9000/api/"
    }
}


fun main() {
    val service = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build().create(TripService::class.java)

    val tripItemDto = TripItemDto(
        helpjoo = UserIdDto(
            "1"
        ),
        source = LatLngDto(
            lat = 1.0,
            lon = 1.0
        ),
        destination = LatLngDto(
            lat = 1.0,
            lon = 1.0
        )
    )
    service.createNewTrip(tripItemDto, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNTYyNjAwNTcyLCJqdGkiOiJmNmY5YjVmNjZlYTI0MDY2OTI4YmZmN2E5NjIyNGRlMCIsInVzZXJfaWQiOiIxIiwiaWQiOjF9.keAXiLU0CUd2PGsrcH07A7Bf4vDjfz77ITmgepqoCKc").subscribe({it -> println(it)}, {it -> println(it)})
}