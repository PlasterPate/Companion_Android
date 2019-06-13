package com.dalisyron.remote.dto.place


import com.google.gson.annotations.SerializedName

data class PlaceAutoCompeleteResponseDto(
    @SerializedName("predictions")
    val predictions: List<Prediction>,
    @SerializedName("status")
    val status: String
)