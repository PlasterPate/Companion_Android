package com.dalisyron.remote.api

import com.dalisyron.remote.dto.user.*
import com.google.gson.JsonObject
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
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
        "9999921"
    )
    service.register(userRegisterItemDto).subscribeWith(object : DisposableSingleObserver<UserRegisterResponseDto>() {
        override fun onSuccess(t: UserRegisterResponseDto) {
            println(t)
        }

        override fun onError(e: Throwable) {
            if (e is HttpException) {
                val responseBody = e.response().errorBody()
                println(responseBody.toString())
            }
        }
    })

    service.login(userLoginItemDto).subscribe({ response ->
        println(response)
    }, {it -> println(it)})
}

