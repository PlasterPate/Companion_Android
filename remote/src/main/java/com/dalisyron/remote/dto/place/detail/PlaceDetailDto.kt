package com.dalisyron.remote.dto.place.detail


import com.google.gson.annotations.SerializedName

data class PlaceDetailDto(
    @SerializedName("geometry")
    val geometry: GeometryDto,
    @SerializedName("name")
    val name: String
)