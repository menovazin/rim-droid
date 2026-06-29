package com.rim.droid.domain.usecase

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Episode
import com.rim.droid.domain.repository.EpisodeRepository
import kotlinx.coroutines.flow.Flow

class GetEpisodes(
    private val repository: EpisodeRepository,
) {
    operator fun invoke(): Flow<PagingData<Episode>> = repository.getEpisodesStream()
}
