package com.rim.droid.domain.usecase

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Location
import com.rim.droid.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocations(
    private val repository: LocationRepository,
) {
    operator fun invoke(): Flow<PagingData<Location>> = repository.getLocationsStream()
}
