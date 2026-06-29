package com.rim.droid.data.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("dimension") val dimension: String = "",
    @SerializedName("residents") val residents: List<String> = emptyList(),
)
