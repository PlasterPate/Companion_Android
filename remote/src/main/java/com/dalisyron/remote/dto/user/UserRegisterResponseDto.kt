package com.dalisyron.remote.dto.user

import com.google.gson.annotations.SerializedName

data class UserRegisterResponseDto(
    @SerializedName("id")
    val id : String?,
    @SerializedName("username")
    val username : List<String>?
)