package com.rim.droid.domain.repository

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationsStream(): Flow<PagingData<Location>>
}
