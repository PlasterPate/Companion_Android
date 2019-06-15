package com.dalisyron.remote.api

import com.dalisyron.remote.dto.user.*
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/api/register/")
    fun register(@Body userRegisterItemDto: UserRegisterItemDto) : Single<UserRegisterResponseDto>

    @POST("/api/login/")
    fun login(@Body userLoginItemDto: UserLoginItemDto) : Single<UserLoginResponseDto>

    companion object {
        const val BASE_URL : String = "http://194.225.229.210:9000/api/"
    }
}

fun main() {
    val service = Retrofit.Builder()
        .baseUrl(UserService.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(UserService::class.java)

    val userLoginItemDto = UserLoginItemDto("1", "123")
    val userRegisterItemDto = UserRegisterItemDto(
        "http://www",
        "mobindh@outlook.com",
        "mobin",
        "dariush",
        "password1",
        "99999"
    )
    service.register(userRegisterItemDto).subscribe({ response ->
        println(response)
    }, {it -> println(it)})

    service.login(userLoginItemDto).subscribe({ response ->
        println(response)
    }, {it -> println(it)})
}