package com.dalisyron.data.model

data class UserRegisterResponseEntity(
    val id: Int,
    val fullName: String?,
    val email: String?,
    val error: String?
)