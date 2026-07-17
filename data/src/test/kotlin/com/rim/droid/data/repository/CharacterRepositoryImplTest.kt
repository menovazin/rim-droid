package com.rim.droid.data.repository

import androidx.paging.testing.asSnapshot
import com.rim.droid.data.BuildConfig
import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.dto.CharacterDto
import com.rim.droid.data.dto.InfoDto
import com.rim.droid.data.dto.NamedRefDto
import com.rim.droid.data.dto.PagedResponseDto
import com.rim.droid.domain.entity.Character
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * spec: rim-rest-data-layer / Character page fetch
 */
class CharacterRepositoryImplTest {

    private val api: RickAndMortyApi = mock()
    private lateinit var repository: CharacterRepositoryImpl

    @Before
    fun setUp() {
        repository = CharacterRepositoryImpl(api)
    }

    @Test
    fun `stream emits mapped domain items`() = runTest {
        whenever(api.getCharacters(any())).thenReturn(
            PagedResponseDto(
                info = InfoDto(pages = 1, next = null),
                results = listOf(rickDto, mortyDto),
            ),
        )

        val items = repository.getCharactersStream().asSnapshot()

        assertThat(items).containsExactly(rickDomain, mortyDomain).inOrder()
    }

    @Test
    fun `stream paginates to second page`() = runTest {
        whenever(api.getCharacters(any()))
            .thenReturn(
                PagedResponseDto(
                    info = InfoDto(pages = 2, next = "${BuildConfig.BASE_URL}character?page=2"),
                    results = listOf(rickDto),
                ),
            )
            .thenReturn(
                PagedResponseDto(
                    info = InfoDto(pages = 2, next = null),
                    results = listOf(mortyDto),
                ),
            )

        val items = repository.getCharactersStream().asSnapshot()

        assertThat(items).containsExactly(rickDomain, mortyDomain).inOrder()
    }

    private companion object {
        val rickDto = CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://example.com/1.jpeg",
            origin = NamedRefDto("Earth"),
            location = NamedRefDto("Citadel of Ricks"),
            episode = listOf("${BuildConfig.BASE_URL}episode/1"),
        )
        val mortyDto = CharacterDto(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://example.com/2.jpeg",
            origin = NamedRefDto("Earth"),
            location = NamedRefDto("Earth"),
            episode = emptyList(),
        )
        val rickDomain = Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://example.com/1.jpeg",
            origin = "Earth",
            location = "Citadel of Ricks",
            episodeIds = listOf(1),
        )
        val mortyDomain = Character(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://example.com/2.jpeg",
            origin = "Earth",
            location = "Earth",
            episodeIds = emptyList(),
        )
    }
}
