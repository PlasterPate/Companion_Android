package com.dalisyron.remote.api

import com.dalisyron.remote.dto.user.UserLoginItemDto
import com.dalisyron.remote.dto.user.UserLoginResponseDto
import com.dalisyron.remote.dto.user.UserRegisterItemDto
import com.dalisyron.remote.dto.user.UserRegisterResponseDto
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST
    fun login(@Body userLoginItemDto : UserLoginItemDto) : Single<UserLoginResponseDto>

    @POST
    fun register(@Body userRegisterItemDto: UserRegisterItemDto) : Single<UserRegisterResponseDto>

    companion object {
        const val BASE_URL : String = "http://api.com"
    }
}