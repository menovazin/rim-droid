package com.rim.droid.presentation.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rim.droid.domain.entity.Location
import com.rim.droid.domain.usecase.GetLocations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    getLocations: GetLocations,
) : ViewModel() {

    val locations: Flow<PagingData<Location>> = getLocations()
        .cachedIn(viewModelScope)
}
