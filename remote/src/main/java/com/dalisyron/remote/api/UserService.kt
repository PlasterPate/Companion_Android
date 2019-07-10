package com.dalisyron.remote.api

import com.dalisyron.remote.dto.token.RefreshAccessItemDto
import com.dalisyron.remote.dto.token.RefreshAccessResponseDto
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
import retrofit2.http.*
import javax.print.attribute.standard.JobOriginatingUserName

interface UserService {

    @POST("/api/register/")
    fun register(@Body userRegisterItemDto: UserRegisterItemDto) : Single<UserRegisterResponseDto>

    @POST("/api/login/")
    fun login(@Body userLoginItemDto: UserLoginItemDto) : Single<UserLoginResponseDto>

    @POST("/api/users/")
    fun getUser(@Body userGetDto : UserGetDto, @Header("Authorization") bearerToken: String) : Single<UserResponseDto>

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

    val userLoginItemDto = UserLoginItemDto("09334537343", "123")
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

    val item = UserGetDto("09334537343")
    service.getUser(item, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNTYyNzQwMzQ1LCJqdGkiOiIyMDUxODJjYzU4MWM0MjZmOWY0MTk3NmY0YTNhOTA4NSIsInVzZXJfaWQiOiIwOTMzNDUzNzM0MyIsImlkIjoxfQ.ImAPp2l1yLjgT_A5fO56lcIVGSNhDcWiBRSO6sWls1Q").subscribe({ response ->
        println(response)
    }, {it -> println(it)})

}