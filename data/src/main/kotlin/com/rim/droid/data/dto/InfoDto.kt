package com.rim.droid.data.dto

import com.google.gson.annotations.SerializedName

data class InfoDto(
    @SerializedName("count") val count: Int = 0,
    @SerializedName("pages") val pages: Int = 0,
    @SerializedName("next") val next: String? = null,
    @SerializedName("prev") val prev: String? = null,
)

data class PagedResponseDto<T>(
    @SerializedName("info") val info: InfoDto = InfoDto(),
    @SerializedName("results") val results: List<T> = emptyList(),
)
