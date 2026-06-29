package com.rim.droid.domain.entity

/**
 * Domain model for a Rick and Morty location.
 */
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentIds: List<Int>,
)
