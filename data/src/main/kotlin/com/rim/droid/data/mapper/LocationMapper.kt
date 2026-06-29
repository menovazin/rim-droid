package com.rim.droid.data.mapper

import com.rim.droid.data.dto.LocationDto
import com.rim.droid.domain.entity.Location

fun LocationDto.toDomain(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentIds = residents.toIds(),
)
