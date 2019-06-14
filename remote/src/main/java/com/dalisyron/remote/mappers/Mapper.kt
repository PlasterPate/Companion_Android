package com.dalisyron.remote.mappers

import com.dalisyron.data.model.*
import com.dalisyron.remote.dto.place.detail.PlaceDetailDto
import com.dalisyron.remote.dto.user.UserLoginItemDto
import com.dalisyron.remote.dto.user.UserLoginResponseDto
import com.dalisyron.remote.dto.user.UserRegisterItemDto
import com.dalisyron.remote.dto.user.UserRegisterResponseDto


fun UserLoginInfoEntity.toUserLoginInfoDto(): UserLoginItemDto {
    return UserLoginItemDto(
        email = email,
        password = password
    )
}

fun UserRegisterInfoEntity.toUserRegisterInfoDto(): UserRegisterItemDto {
    return UserRegisterItemDto(
        fullName = fullName,
        email = email,
        password = password
    )
}


fun UserLoginResponseDto.toUserLoginResponseEntity(): UserLoginResponseEntity? {
    return UserLoginResponseEntity(
        id = id,
        fullName = fullName,
        email = email,
        error = error?.message
    )
}

fun UserRegisterResponseDto.toUserRegisterResponseEntity(): UserRegisterResponseEntity? {
    return UserRegisterResponseEntity(
        id = id,
        fullName = fullName,
        email = email,
        error = error?.message
    )
}

fun PlaceDetailDto.toPlaceEntity() : PlaceEntity {
    return PlaceEntity(
        description = name,
        lat = geometry.location.lat,
        lng = geometry.location.lng
    )
}

