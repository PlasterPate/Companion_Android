package com.dalisyron.remote.api

import com.dalisyron.remote.api.TokenService.Companion.BASE_URL
import com.dalisyron.remote.dto.token.RefreshAccessItemDto
import com.dalisyron.remote.dto.token.RefreshAccessResponseDto
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {

    @POST("/api/token/refresh/")
    fun refreshAccessToken(@Body refreshAccessItemDto : RefreshAccessItemDto) : Single<RefreshAccessResponseDto?>

    companion object {
        const val BASE_URL = "http://194.225.229.210:9000/api/"
    }
}