package com.dalisyron.remote.mappers

import com.dalisyron.remote.dto.user.*

fun UserLoginItemEntity.toUserLoginItemDto() : UserLoginItemDto {
    return UserLoginItemDto(
        username = username,
        password = password
    )
}

fun UserRegisterItemEntity.toUserRegisterItemDto() : UserRegisterItemDto {
    return UserRegisterItemDto(
        avatar = avatar,
        password = password,
        email = email,
        firstName = firstName,
        lastName = lastName,
        username = username
    )
}

fun UserLoginResponseDto.toUserLoginResponseEntity() : UserLoginResponseEntity {
    return UserLoginResponseEntity(
        access = access,
        detail = detail,
        id = id,
        refresh = refresh
    )
}

fun UserRegisterResponseDto.toUserRegisterResponseEntity() : UserRegisterResponseEntity {
    return UserRegisterResponseEntity(
        id = id,
        username = username
    )
}