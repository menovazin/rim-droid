package com.rim.droid.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.paging.LocationPagingSource
import com.rim.droid.domain.entity.Location
import com.rim.droid.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
) : LocationRepository {

    override fun getLocationsStream(): Flow<PagingData<Location>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { LocationPagingSource(api) },
    ).flow

    private companion object {
        const val PAGE_SIZE = 20
    }
}
