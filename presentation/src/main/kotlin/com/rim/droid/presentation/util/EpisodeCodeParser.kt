package com.rim.droid.presentation.util

/**
 * Parses episode codes in the format "S01E05" into season and episode numbers.
 */
val String.season: Int
    get() {
        val match = Regex("S(\\d+)E(\\d+)").find(this) ?: return 0
        return match.groupValues[1].toIntOrNull() ?: 0
    }

val String.episodeNumber: Int
    get() {
        val match = Regex("S(\\d+)E(\\d+)").find(this) ?: return 0
        return match.groupValues[2].toIntOrNull() ?: 0
    }
