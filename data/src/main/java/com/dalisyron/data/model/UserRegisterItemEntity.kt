package com.dalisyron.remote.dto.user

data class UserRegisterItemEntity(
    val avatar: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val username: String
)