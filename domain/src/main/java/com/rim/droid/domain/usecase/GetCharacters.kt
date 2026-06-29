package com.rim.droid.domain.usecase

import androidx.paging.PagingData
import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

/**
 * Exposes the paginated stream of characters to the presentation layer.
 */
class GetCharacters(
    private val repository: CharacterRepository,
) {
    operator fun invoke(): Flow<PagingData<Character>> = repository.getCharactersStream()
}
