package com.rim.droid.domain.entity

import kotlinx.serialization.Serializable

/**
 * Domain model for a Rick and Morty location.
 */
@Serializable
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentIds: List<Int>,
)
