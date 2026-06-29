package com.rim.droid.domain.repository

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Episode
import com.rim.droid.domain.entity.PagedResult
import com.rim.droid.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    suspend fun getEpisodes(page: Int): Result<PagedResult<Episode>>

    fun getEpisodesStream(): Flow<PagingData<Episode>>
}
