package com.rim.droid.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.mapper.toDomain
import com.rim.droid.data.paging.LocationPagingSource
import com.rim.droid.data.util.safeApiCall
import com.rim.droid.domain.entity.Location
import com.rim.droid.domain.entity.PagedResult
import com.rim.droid.domain.repository.LocationRepository
import com.rim.droid.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
) : LocationRepository {

    override suspend fun getLocations(page: Int): Result<PagedResult<Location>> = safeApiCall {
        val response = api.getLocations(page)
        PagedResult(
            items = response.results.map { it.toDomain() },
            totalPages = response.info.pages,
            nextPage = response.info.next?.let { page + 1 },
        )
    }

    override fun getLocationsStream(): Flow<PagingData<Location>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { LocationPagingSource(api) },
    ).flow

    private companion object {
        const val PAGE_SIZE = 20
    }
}
