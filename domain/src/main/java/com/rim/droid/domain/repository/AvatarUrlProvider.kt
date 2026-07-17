package com.rim.droid.domain.repository

/**
 * Resolves character avatar image URLs for UI display.
 *
 * Implementations live in the data layer (base URL / path policy).
 */
interface AvatarUrlProvider {
    fun fromCharacterId(id: Int): String
}
