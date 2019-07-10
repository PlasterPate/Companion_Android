package com.dalisyron.remote.dto.trip

import com.dalisyron.remote.dto.user.UserIdDto

data class TripItemDto(
    val helpjoo : UserIdDto,
    val source : LatLngDto,
    val destination: LatLngDto
)