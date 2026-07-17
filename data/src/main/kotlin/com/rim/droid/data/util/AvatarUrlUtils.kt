package com.rim.droid.data.util

import com.rim.droid.data.BuildConfig

/**
 * URL substitution utilities for Rick and Morty character avatars.
 *
 * Mirrors the Flutter [AvatarUrlUtils] behavior:
 * - [getCustomAvatarUrl] makes relative image URLs absolute using the API base URL.
 * - [avatarUrlFromId] builds a character avatar URL from an id.
 */
object AvatarUrlUtils {

    /**
     * Returns [originalUrl] unchanged if it is already absolute.
     * If [originalUrl] is relative (starts with "/"), prepends [BuildConfig.BASE_URL].
     */
    fun getCustomAvatarUrl(originalUrl: String): String = if (originalUrl.startsWith("/")) {
        "${BuildConfig.BASE_URL}${originalUrl.trimStart('/')}"
    } else {
        originalUrl
    }

    /**
     * Builds a character avatar URL for the given [id] using the API base URL.
     */
    fun avatarUrlFromId(id: Int): String = "${BuildConfig.BASE_URL}character/avatar/$id.jpeg"
}
