package com.rim.droid.domain.repository

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Location
import com.rim.droid.domain.entity.PagedResult
import com.rim.droid.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getLocations(page: Int): Result<PagedResult<Location>>

    fun getLocationsStream(): Flow<PagingData<Location>>
}
