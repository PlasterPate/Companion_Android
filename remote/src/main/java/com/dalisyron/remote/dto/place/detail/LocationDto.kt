package com.dalisyron.remote.dto.place.detail


import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)