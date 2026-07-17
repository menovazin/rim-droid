package com.rim.droid.data.repository

import androidx.paging.testing.asSnapshot
import com.rim.droid.data.BuildConfig
import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.dto.EpisodeDto
import com.rim.droid.data.dto.InfoDto
import com.rim.droid.data.dto.PagedResponseDto
import com.rim.droid.domain.entity.Episode
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * spec: rim-rest-data-layer / Episode page fetch
 */
class EpisodeRepositoryImplTest {

    private val api: RickAndMortyApi = mock()
    private lateinit var repository: EpisodeRepositoryImpl

    @Before
    fun setUp() {
        repository = EpisodeRepositoryImpl(api)
    }

    @Test
    fun `stream emits mapped domain items`() = runTest {
        whenever(api.getEpisodes(any())).thenReturn(
            PagedResponseDto(
                info = InfoDto(pages = 1, next = null),
                results = listOf(pilotDto, lawnMortyDto),
            ),
        )

        val items = repository.getEpisodesStream().asSnapshot()

        assertThat(items).containsExactly(pilotDomain, lawnMortyDomain).inOrder()
    }

    @Test
    fun `stream paginates to second page`() = runTest {
        whenever(api.getEpisodes(any()))
            .thenReturn(
                PagedResponseDto(
                    info = InfoDto(pages = 2, next = "${BuildConfig.BASE_URL}episode?page=2"),
                    results = listOf(pilotDto),
                ),
            )
            .thenReturn(
                PagedResponseDto(
                    info = InfoDto(pages = 2, next = null),
                    results = listOf(lawnMortyDto),
                ),
            )

        val items = repository.getEpisodesStream().asSnapshot()

        assertThat(items).containsExactly(pilotDomain, lawnMortyDomain).inOrder()
    }

    private companion object {
        val pilotDto = EpisodeDto(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            characters = listOf("${BuildConfig.BASE_URL}character/1"),
        )
        val lawnMortyDto = EpisodeDto(
            id = 2,
            name = "Lawnmower Dog",
            airDate = "December 9, 2013",
            episode = "S01E02",
            characters = emptyList(),
        )
        val pilotDomain = Episode(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episodeCode = "S01E01",
            characterIds = listOf(1),
        )
        val lawnMortyDomain = Episode(
            id = 2,
            name = "Lawnmower Dog",
            airDate = "December 9, 2013",
            episodeCode = "S01E02",
            characterIds = emptyList(),
        )
    }
}
