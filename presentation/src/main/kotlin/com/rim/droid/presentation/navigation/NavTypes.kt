package com.rim.droid.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.entity.Episode
import com.rim.droid.domain.entity.Location
import kotlinx.serialization.json.Json

private val navJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

val CharacterNavType = object : NavType<Character>(isNullableAllowed = false) {
    override fun parseValue(value: String): Character = navJson.decodeFromString<Character>(value)
    override fun serializeAsValue(value: Character): String =
        Uri.encode(navJson.encodeToString(Character.serializer(), value))
    override fun get(bundle: Bundle, key: String): Character? =
        bundle.getString(key)?.let { navJson.decodeFromString<Character>(it) }
    override fun put(bundle: Bundle, key: String, value: Character) {
        bundle.putString(key, navJson.encodeToString(Character.serializer(), value))
    }
}

val EpisodeNavType = object : NavType<Episode>(isNullableAllowed = false) {
    override fun parseValue(value: String): Episode = navJson.decodeFromString<Episode>(value)
    override fun serializeAsValue(value: Episode): String =
        Uri.encode(navJson.encodeToString(Episode.serializer(), value))
    override fun get(bundle: Bundle, key: String): Episode? =
        bundle.getString(key)?.let { navJson.decodeFromString<Episode>(it) }
    override fun put(bundle: Bundle, key: String, value: Episode) {
        bundle.putString(key, navJson.encodeToString(Episode.serializer(), value))
    }
}

val LocationNavType = object : NavType<Location>(isNullableAllowed = false) {
    override fun parseValue(value: String): Location = navJson.decodeFromString<Location>(value)
    override fun serializeAsValue(value: Location): String =
        Uri.encode(navJson.encodeToString(Location.serializer(), value))
    override fun get(bundle: Bundle, key: String): Location? =
        bundle.getString(key)?.let { navJson.decodeFromString<Location>(it) }
    override fun put(bundle: Bundle, key: String, value: Location) {
        bundle.putString(key, navJson.encodeToString(Location.serializer(), value))
    }
}
