package com.rim.droid.domain.repository

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.entity.PagedResult
import com.rim.droid.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    /** Single page fetch with pagination metadata. */
    suspend fun getCharacters(page: Int): Result<PagedResult<Character>>

    /** Paging3 stream for infinite scroll. */
    fun getCharactersStream(): Flow<PagingData<Character>>
}
