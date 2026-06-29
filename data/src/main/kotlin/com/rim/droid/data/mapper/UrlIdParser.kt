package com.rim.droid.data.mapper

/**
 * Extracts the trailing numeric id from a Rick and Morty resource URL, e.g.
 * `https://rickandmortyapi.com/api/episode/12` -> 12.
 */
internal fun String.trailingIdOrNull(): Int? = trimEnd('/').substringAfterLast('/').toIntOrNull()

internal fun List<String>.toIds(): List<Int> = mapNotNull { it.trailingIdOrNull() }
