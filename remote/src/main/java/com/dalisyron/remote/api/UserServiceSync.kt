package com.dalisyron.remote.api

import com.dalisyron.remote.NetworkManager
import com.dalisyron.remote.dto.user.UserLoginItemDto
import com.dalisyron.remote.dto.user.UserLoginResponseDto
import com.google.gson.Gson
import sun.nio.ch.Net


class UserServiceSync(private val networkManager: NetworkManager) {

    fun login(userLoginItemDto: UserLoginItemDto) : UserLoginResponseDto {
        val jsonLoginParameter = Gson().toJson(userLoginItemDto)
        val jsonResponseString = networkManager.post("http://194.225.229.210:9000/api/api/login", jsonLoginParameter)
        println(jsonResponseString)
        return Gson().fromJson(jsonResponseString, UserLoginResponseDto::class.java)
    }

}

fun main() {
    val service = UserServiceSync(NetworkManager())
    val userLoginItemDto = UserLoginItemDto("1", "1234")

    println(service.login(userLoginItemDto))
}