package com.dalisyron.remote.dto.token

import com.google.gson.annotations.SerializedName

data class RefreshAccessItemDto(
    @SerializedName("refresh")
    val refresh : String
)