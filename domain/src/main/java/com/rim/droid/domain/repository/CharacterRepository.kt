package com.rim.droid.domain.repository

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    /** Paging3 stream for infinite scroll. */
    fun getCharactersStream(): Flow<PagingData<Character>>
}
