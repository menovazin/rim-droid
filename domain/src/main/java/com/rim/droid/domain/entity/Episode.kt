package com.rim.droid.domain.entity

/**
 * Domain model for a Rick and Morty episode.
 */
data class Episode(
    val id: Int,
    val name: String,
    val episodeCode: String,
    val airDate: String,
    val characterIds: List<Int>,
)
