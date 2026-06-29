package com.rim.droid.data.dto

import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("status") val status: String = "",
    @SerializedName("species") val species: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("gender") val gender: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("origin") val origin: NamedRefDto = NamedRefDto(),
    @SerializedName("location") val location: NamedRefDto = NamedRefDto(),
    @SerializedName("episode") val episode: List<String> = emptyList(),
)

data class NamedRefDto(
    @SerializedName("name") val name: String = "",
    @SerializedName("url") val url: String = "",
)
