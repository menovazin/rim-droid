package com.rim.droid.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.mapper.toDomain
import com.rim.droid.data.util.safeApiCall
import com.rim.droid.data.paging.CharacterPagingSource
import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.entity.PagedResult
import com.rim.droid.domain.repository.CharacterRepository
import com.rim.droid.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi,
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): Result<PagedResult<Character>> = safeApiCall {
        val response = api.getCharacters(page)
        PagedResult(
            items = response.results.map { it.toDomain() },
            totalPages = response.info.pages,
            nextPage = response.info.next?.let { page + 1 },
        )
    }

    override fun getCharactersStream(): Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { CharacterPagingSource(api) },
    ).flow

    private companion object {
        const val PAGE_SIZE = 20
    }
}
