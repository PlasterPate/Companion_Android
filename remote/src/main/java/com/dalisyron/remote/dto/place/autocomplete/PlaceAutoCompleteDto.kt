package com.dalisyron.remote.dto.place.autocomplete


import com.google.gson.annotations.SerializedName

data class PlaceAutoCompleteDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("matched_substrings")
    val matchedSubstrings: List<MatchedSubstringDto>,
    @SerializedName("place_id")
    val placeId: String,
    @SerializedName("reference")
    val reference: String,
    @SerializedName("structured_formatting")
    val structuredFormatting: StructuredFormattingDto,
    @SerializedName("terms")
    val terms: List<TermDto>,
    @SerializedName("types")
    val types: List<String>
)