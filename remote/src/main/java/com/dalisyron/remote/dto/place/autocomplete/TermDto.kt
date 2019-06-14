package com.dalisyron.remote.dto.place.autocomplete


import com.google.gson.annotations.SerializedName

data class TermDto(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("value")
    val value: String
)