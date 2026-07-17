package com.rim.droid.presentation.smoke

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.entity.Episode
import com.rim.droid.domain.entity.Location
import com.rim.droid.domain.repository.AvatarUrlProvider
import com.rim.droid.domain.repository.CharacterRepository
import com.rim.droid.domain.repository.EpisodeRepository
import com.rim.droid.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import java.io.IOException

/**
 * Catalog fakes that always fail the first page load — exercises list error chrome
 * without a network server.
 */
class FailingCharacterRepository : CharacterRepository {
    override fun getCharactersStream(): Flow<PagingData<Character>> =
        failingPager { IOException("fake characters failure") }
}

class FailingEpisodeRepository : EpisodeRepository {
    override fun getEpisodesStream(): Flow<PagingData<Episode>> =
        failingPager { IOException("fake episodes failure") }
}

class FailingLocationRepository : LocationRepository {
    override fun getLocationsStream(): Flow<PagingData<Location>> =
        failingPager { IOException("fake locations failure") }
}

class FakeAvatarUrlProvider : AvatarUrlProvider {
    override fun fromCharacterId(id: Int): String =
        "https://example.test/avatar/$id.jpeg"
}

private fun <T : Any> failingPager(error: () -> Throwable): Flow<PagingData<T>> =
    Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = {
            object : PagingSource<Int, T>() {
                override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
                    LoadResult.Error(error())
            }
        },
    ).flow
