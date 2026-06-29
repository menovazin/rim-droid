package com.rim.droid.presentation.ui.episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.mapper.toDomain
import com.rim.droid.domain.entity.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val api: RickAndMortyApi,
) : ViewModel() {

    private val episodeId: Int = savedStateHandle["episodeId"] ?: 0

    private val _episode = MutableStateFlow<Episode?>(null)
    val episode: StateFlow<Episode?> = _episode.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val dto = api.getEpisode(episodeId)
                _episode.value = dto.toDomain()
            } catch (_: Exception) { }
        }
    }
}
