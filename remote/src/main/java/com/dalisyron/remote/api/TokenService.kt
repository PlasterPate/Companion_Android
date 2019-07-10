package com.dalisyron.remote.api

import com.dalisyron.remote.api.TokenService.Companion.BASE_URL
import com.dalisyron.remote.dto.token.RefreshAccessItemDto
import com.dalisyron.remote.dto.token.RefreshAccessResponseDto
import com.dalisyron.remote.dto.token.SendAccessTokenResponseDto
import com.dalisyron.remote.dto.token.TokenItemDto
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenService {

    @POST("/api/token/refresh/")
    fun refreshAccessToken(@Body refreshAccessItemDto : RefreshAccessItemDto) : Single<RefreshAccessResponseDto?>

    @POST("/api/notification/token/")
    fun sendAccessToken(@Body tokenItemDto : TokenItemDto,@Header("Authorization") bearerToken: String) : Single<Unit>

    companion object {
        const val BASE_URL = "http://194.225.229.210:9000/api/"
    }
}

fun main() {
    val service = Retrofit.Builder()
        .baseUrl(TokenService.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(TokenService::class.java)

    service.sendAccessToken(TokenItemDto("dF7xmLF_TkI:APA91bFW2jDPJsmXe87F0TdPc7siFMbsXozZmtU4C9ymL3IzvFP1ZgRouSM5lSG-FxjwaUfTuc179oaqvZktxJ0KJrZXSXKOciDU3YdVSmkMa6s1Us62qaG9ykmCGN8j1egqlWeLNJaT"),"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNTYyNzA5NDQyLCJqdGkiOiI2YjgxNTc0ZTJkNWY0M2M4OTNjN2Y3NDIxMWU0Yjk0NCIsInVzZXJfaWQiOiIwOTMzNDUzNzM0MyIsImlkIjoxfQ.0E_ml7TM2kQQN2XaxN_GkTqwkP9kPHcIu3ELL2fdGDY").subscribe({response -> println("success $response")}, {error -> println(error)})
}