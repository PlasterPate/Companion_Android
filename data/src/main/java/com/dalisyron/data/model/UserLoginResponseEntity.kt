package com.dalisyron.remote.dto.user

data class UserLoginResponseEntity(
    val access: String,
    val refresh: String,
    val id: String,
    val detail : String?
)