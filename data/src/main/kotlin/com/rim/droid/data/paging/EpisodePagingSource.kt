package com.rim.droid.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.mapper.toDomain
import com.rim.droid.domain.entity.Episode
import kotlinx.coroutines.CancellationException

class EpisodePagingSource(
    private val api: RickAndMortyApi,
) : PagingSource<Int, Episode>() {

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? =
        state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val page = params.key ?: FIRST_PAGE
        return try {
            val response = api.getEpisodes(page)
            val items = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = items,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1,
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val FIRST_PAGE = 1
    }
}
