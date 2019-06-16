package com.dalisyron.remote.mappers

import com.dalisyron.data.model.PlaceEntity
import com.dalisyron.remote.dto.place.detail.PlaceDetailDto
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
        id = id,
        refresh = refresh
    )
}

fun UserRegisterResponseDto.toUserRegisterResponseEntity() : UserRegisterResponseEntity {
    return UserRegisterResponseEntity(
        id = id
    )
}

fun PlaceDetailDto.toPlaceEntity() : PlaceEntity {
    return PlaceEntity(
        description = name,
        lat = geometry.location.lat,
        lng = geometry.location.lng
    )
}

