package com.dalisyron.remote.dto.user

import com.google.gson.annotations.SerializedName

data class UserRegisterItemDto(
    val fullName: String,
    val email: String,
    val password: String
)