package com.rim.droid.data.mapper

import com.rim.droid.data.dto.CharacterDto
import com.rim.droid.data.util.AvatarUrlUtils
import com.rim.droid.domain.entity.Character

fun CharacterDto.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = AvatarUrlUtils.getCustomAvatarUrl(image),
    origin = origin.name,
    location = location.name,
    episodeIds = episode.toIds(),
)
