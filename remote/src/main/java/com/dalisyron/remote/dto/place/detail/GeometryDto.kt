package com.dalisyron.remote.dto.place.detail


import com.google.gson.annotations.SerializedName

data class GeometryDto(
    @SerializedName("location")
    val location: LocationDto,
    @SerializedName("viewport")
    val viewport: ViewportDto
)