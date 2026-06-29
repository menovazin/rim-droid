package com.rim.droid.presentation.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.usecase.GetCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    getCharacters: GetCharacters,
) : ViewModel() {

    val characters: Flow<PagingData<Character>> = getCharacters()
        .cachedIn(viewModelScope)
}
