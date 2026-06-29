package com.rim.droid.presentation.ui.characters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.mapper.toDomain
import com.rim.droid.domain.entity.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val api: RickAndMortyApi,
) : ViewModel() {

    private val characterId: Int = savedStateHandle["characterId"] ?: 0

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val dto = api.getCharacter(characterId)
                _character.value = dto.toDomain()
            } catch (_: Exception) { }
        }
    }
}
