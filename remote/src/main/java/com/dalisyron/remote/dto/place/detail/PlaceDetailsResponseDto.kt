package com.dalisyron.remote.dto.place.detail


import com.google.gson.annotations.SerializedName

data class PlaceDetailsResponseDto(
    @SerializedName("html_attributions")
    val htmlAttributions: List<Any>,
    @SerializedName("result")
    val result: PlaceDetailDto,
    @SerializedName("status")
    val status: String
)