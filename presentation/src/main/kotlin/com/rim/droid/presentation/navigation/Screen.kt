package com.rim.droid.presentation.navigation

import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.entity.Episode
import com.rim.droid.domain.entity.Location
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object HomeRoute

@Serializable
data class CharacterDetailRoute(val character: Character)

@Serializable
data class EpisodeDetailRoute(val episode: Episode)

@Serializable
data class LocationDetailRoute(val location: Location)
