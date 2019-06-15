package com.dalisyron.remote.dto.place.autocomplete


import com.google.gson.annotations.SerializedName

data class PlaceAutoCompleteResponseDto(
    @SerializedName("predictions")
    val items: List<PlaceAutoCompleteDto>,
    @SerializedName("status")
    val status: String
)