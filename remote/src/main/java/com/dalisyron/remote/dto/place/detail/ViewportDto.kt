package com.dalisyron.remote.dto.place.detail


import com.google.gson.annotations.SerializedName

data class ViewportDto(
    @SerializedName("northeast")
    val northeast: NortheastDto,
    @SerializedName("southwest")
    val southwest: SouthwestDto
)