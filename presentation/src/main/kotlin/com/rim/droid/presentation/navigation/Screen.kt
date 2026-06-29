package com.rim.droid.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object HomeRoute

@Serializable
data class CharacterDetailRoute(val characterId: Int)

@Serializable
data class EpisodeDetailRoute(val episodeId: Int)

@Serializable
data class LocationDetailRoute(val locationId: Int)
