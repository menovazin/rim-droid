package com.rim.droid.presentation.util

/**
 * Builds an avatar URL for a Rick and Morty character via the semester.syazy.com CDN.
 */
object AvatarUrlUtils {
    private const val BASE = "https://semester.syazy.com/rickandmorty"

    fun avatarUrlFromId(id: Int): String = "$BASE/$id.jpeg"
}
