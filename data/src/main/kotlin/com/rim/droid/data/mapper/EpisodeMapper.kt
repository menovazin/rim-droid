package com.rim.droid.data.mapper

import com.rim.droid.data.dto.EpisodeDto
import com.rim.droid.domain.entity.Episode

fun EpisodeDto.toDomain(): Episode = Episode(
    id = id,
    name = name,
    episodeCode = episode,
    airDate = airDate,
    characterIds = characters.toIds(),
)
