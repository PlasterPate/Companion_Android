package com.dalisyron.remote.dto.place.autocomplete


import com.google.gson.annotations.SerializedName

data class StructuredFormattingDto(
    @SerializedName("main_text")
    val mainText: String,
    @SerializedName("main_text_matched_substrings")
    val mainTextMatchedSubstrings: List<MainTextMatchedSubstringDto>,
    @SerializedName("secondary_text")
    val secondaryText: String
)