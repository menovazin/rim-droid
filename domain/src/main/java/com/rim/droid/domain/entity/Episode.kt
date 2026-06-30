package com.rim.droid.domain.entity

import kotlinx.serialization.Serializable

/**
 * Domain model for a Rick and Morty episode.
 */
@Serializable
data class Episode(
    val id: Int,
    val name: String,
    val episodeCode: String,
    val airDate: String,
    val characterIds: List<Int>,
)
