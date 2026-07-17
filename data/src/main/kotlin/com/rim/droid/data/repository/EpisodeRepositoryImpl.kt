package com.rim.droid.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.paging.EpisodePagingSource
import com.rim.droid.domain.entity.Episode
import com.rim.droid.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
) : EpisodeRepository {

    override fun getEpisodesStream(): Flow<PagingData<Episode>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { EpisodePagingSource(api) },
    ).flow

    private companion object {
        const val PAGE_SIZE = 20
    }
}
