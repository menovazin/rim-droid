package com.rim.droid.data.paging

import androidx.paging.PagingConfig
import com.rim.droid.data.BuildConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.common.truth.Truth.assertThat
import com.rim.droid.core.test.base.BaseTest
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.dto.CharacterDto
import com.rim.droid.data.dto.InfoDto
import com.rim.droid.data.dto.PagedResponseDto
import com.rim.droid.domain.entity.Character
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * spec: rim-rest-data-layer / Character page fetch
 */
class CharacterPagingSourceTest : BaseTest() {

    private val api: RickAndMortyApi = mock()
    private lateinit var source: CharacterPagingSource

    @Before
    fun setUp() {
        source = CharacterPagingSource(api)
    }

    @Test
    fun `load refresh first page returns nextKey 2`() = runUnconfinedTest {
        whenever(api.getCharacters(any())).thenReturn(pageOne())

        val result = source.load(refresh())

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val page = result as PagingSource.LoadResult.Page
        assertThat(page.prevKey).isNull()
        assertThat(page.nextKey).isEqualTo(2)
        assertThat(page.data).hasSize(2)
    }

    @Test
    fun `load append advances keys`() = runUnconfinedTest {
        whenever(api.getCharacters(any())).thenReturn(pageTwo())

        val result = source.load(append(key = 5))

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val page = result as PagingSource.LoadResult.Page
        assertThat(page.prevKey).isEqualTo(4)
        assertThat(page.nextKey).isEqualTo(6)
    }

    @Test
    fun `load returns null nextKey at end of pagination`() = runUnconfinedTest {
        whenever(api.getCharacters(any())).thenReturn(lastPage())

        val result = source.load(refresh())

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val page = result as PagingSource.LoadResult.Page
        assertThat(page.nextKey).isNull()
    }

    @Test
    fun `load returns error when api throws`() = runUnconfinedTest {
        val thrown = RuntimeException("network")
        whenever(api.getCharacters(any())).thenThrow(thrown)

        val result = source.load(refresh())

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        val error = result as PagingSource.LoadResult.Error
        assertThat(error.throwable).isSameInstanceAs(thrown)
    }

    @Test
    fun `load rethrows CancellationException`() = runUnconfinedTest {
        val thrown = kotlinx.coroutines.CancellationException("cancelled")
        whenever(api.getCharacters(any())).thenThrow(thrown)

        val outcome = runCatching { source.load(refresh()) }
        assertThat(outcome.exceptionOrNull()).isSameInstanceAs(thrown)
    }

    @Test
    fun `getRefreshKey returns key from anchor position`() {
        val state = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(dummyCharacter()),
                    prevKey = 1,
                    nextKey = 3,
                ),
            ),
            anchorPosition = 0,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0,
        )

        assertThat(source.getRefreshKey(state)).isEqualTo(2)
    }

    @Test
    fun `getRefreshKey returns null without anchor`() {
        val state = PagingState<Int, Character>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0,
        )

        assertThat(source.getRefreshKey(state)).isNull()
    }

    private companion object {
        fun refresh() = PagingSource.LoadParams.Refresh<Int>(
            key = null,
            loadSize = 10,
            placeholdersEnabled = false,
        )

        fun append(key: Int) = PagingSource.LoadParams.Append<Int>(
            key = key,
            loadSize = 10,
            placeholdersEnabled = false,
        )

        fun pageOne() = PagedResponseDto(
            info = InfoDto(pages = 2, next = "${BuildConfig.BASE_URL}character?page=2"),
            results = listOf(
                CharacterDto(id = 1, name = "Rick"),
                CharacterDto(id = 2, name = "Morty"),
            ),
        )

        fun pageTwo() = PagedResponseDto(
            info = InfoDto(pages = 10, next = "${BuildConfig.BASE_URL}character?page=6"),
            results = listOf(CharacterDto(id = 11, name = "Summer")),
        )

        fun lastPage() = PagedResponseDto(
            info = InfoDto(pages = 1, next = null),
            results = listOf(CharacterDto(id = 1, name = "Rick")),
        )

        fun dummyCharacter() = Character(
            id = 0,
            name = "",
            status = "",
            species = "",
            type = "",
            gender = "",
            image = "",
            origin = "",
            location = "",
            episodeIds = emptyList(),
        )
    }
}
