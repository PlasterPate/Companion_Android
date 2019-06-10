package com.dalisyron.remote.dto.user

data class UserLoginResponseDto(
    val id : Int,
    val fullName: String?,
    val email: String,
    val error : ErrorDescriptionDto?
)