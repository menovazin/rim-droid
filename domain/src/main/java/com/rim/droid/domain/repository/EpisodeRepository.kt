package com.rim.droid.domain.repository

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    fun getEpisodesStream(): Flow<PagingData<Episode>>
}
