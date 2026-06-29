package com.rim.droid.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.mapper.toDomain
import com.rim.droid.domain.entity.Location

class LocationPagingSource(
    private val api: RickAndMortyApi,
) : PagingSource<Int, Location>() {

    override fun getRefreshKey(state: PagingState<Int, Location>): Int? =
        state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        val page = params.key ?: FIRST_PAGE
        return try {
            val response = api.getLocations(page)
            val items = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = items,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}
