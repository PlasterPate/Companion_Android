package com.dalisyron.remote.dto.user

import com.google.gson.annotations.SerializedName

data class UserLoginResponseDto(
    val id: Int,
    val fullName: String?,
    val email: String?,
    val error: ErrorDescriptionDto?
)