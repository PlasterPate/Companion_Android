package com.dalisyron.remote.dto.place.autocomplete


import com.google.gson.annotations.SerializedName

data class MatchedSubstringDto(
    @SerializedName("length")
    val length: Int,
    @SerializedName("offset")
    val offset: Int
)