package com.rim.droid.domain.entity

/**
 * Domain model for a Rick and Morty character, isolated from the REST schema.
 */
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val origin: String,
    val location: String,
    val episodeIds: List<Int>,
)
