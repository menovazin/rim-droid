package com.rim.droid.presentation.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rim.droid.domain.entity.Episode
import com.rim.droid.domain.usecase.GetEpisodes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    getEpisodes: GetEpisodes,
) : ViewModel() {

    val episodes: Flow<PagingData<Episode>> = getEpisodes()
        .cachedIn(viewModelScope)
}
