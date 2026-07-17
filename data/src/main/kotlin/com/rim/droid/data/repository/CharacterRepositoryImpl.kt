package com.rim.droid.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.paging.CharacterPagingSource
import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
) : CharacterRepository {

    override fun getCharactersStream(): Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { CharacterPagingSource(api) },
    ).flow

    private companion object {
        const val PAGE_SIZE = 20
    }
}
