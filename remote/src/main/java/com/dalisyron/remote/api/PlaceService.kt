package com.dalisyron.remote.api

import com.dalisyron.data.model.PlaceEntity
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PlaceService {
    @POST
    fun getAutoCompletePlaces(@Body searchText: String) : List<PlaceEntity>

    companion object {
        const val BASE_URL : String = "http://api.com"
    }
}