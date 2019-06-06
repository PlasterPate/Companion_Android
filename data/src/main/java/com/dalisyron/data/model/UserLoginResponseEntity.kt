package com.dalisyron.data.model

data class UserLoginResponseEntity(
    val id: Int,
    val fullName: String?,
    val email: String?,
    val error: String?
)