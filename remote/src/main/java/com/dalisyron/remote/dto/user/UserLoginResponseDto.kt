package com.dalisyron.remote.dto.user

import com.google.gson.annotations.SerializedName

data class UserLoginResponseDto(
    @SerializedName("access")
    val access: String,
    @SerializedName("refresh")
    val refresh: String,
    @SerializedName("id")
    val id: String
)