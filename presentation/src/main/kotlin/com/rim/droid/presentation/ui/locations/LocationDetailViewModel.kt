package com.rim.droid.presentation.ui.locations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.mapper.toDomain
import com.rim.droid.domain.entity.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val api: RickAndMortyApi,
) : ViewModel() {

    private val locationId: Int = savedStateHandle["locationId"] ?: 0

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val dto = api.getLocation(locationId)
                _location.value = dto.toDomain()
            } catch (_: Exception) { }
        }
    }
}
