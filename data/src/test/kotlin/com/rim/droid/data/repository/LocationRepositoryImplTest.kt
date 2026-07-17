package com.rim.droid.data.repository

import androidx.paging.testing.asSnapshot
import com.rim.droid.data.BuildConfig
import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.api.RickAndMortyApi
import com.rim.droid.data.dto.InfoDto
import com.rim.droid.data.dto.LocationDto
import com.rim.droid.data.dto.PagedResponseDto
import com.rim.droid.domain.entity.Location
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * spec: rim-rest-data-layer / Location page fetch
 */
class LocationRepositoryImplTest {

    private val api: RickAndMortyApi = mock()
    private lateinit var repository: LocationRepositoryImpl

    @Before
    fun setUp() {
        repository = LocationRepositoryImpl(api)
    }

    @Test
    fun `stream emits mapped domain items`() = runTest {
        whenever(api.getLocations(any())).thenReturn(
            PagedResponseDto(
                info = InfoDto(pages = 1, next = null),
                results = listOf(earthDto, citadelDto),
            ),
        )

        val items = repository.getLocationsStream().asSnapshot()

        assertThat(items).containsExactly(earthDomain, citadelDomain).inOrder()
    }

    @Test
    fun `stream paginates to second page`() = runTest {
        whenever(api.getLocations(any()))
            .thenReturn(
                PagedResponseDto(
                    info = InfoDto(pages = 2, next = "${BuildConfig.BASE_URL}location?page=2"),
                    results = listOf(earthDto),
                ),
            )
            .thenReturn(
                PagedResponseDto(
                    info = InfoDto(pages = 2, next = null),
                    results = listOf(citadelDto),
                ),
            )

        val items = repository.getLocationsStream().asSnapshot()

        assertThat(items).containsExactly(earthDomain, citadelDomain).inOrder()
    }

    private companion object {
        val earthDto = LocationDto(
            id = 1,
            name = "Earth",
            type = "Planet",
            dimension = "Dimension C-137",
            residents = listOf("${BuildConfig.BASE_URL}character/1"),
        )
        val citadelDto = LocationDto(
            id = 2,
            name = "Citadel of Ricks",
            type = "Space station",
            dimension = "Unknown",
            residents = emptyList(),
        )
        val earthDomain = Location(
            id = 1,
            name = "Earth",
            type = "Planet",
            dimension = "Dimension C-137",
            residentIds = listOf(1),
        )
        val citadelDomain = Location(
            id = 2,
            name = "Citadel of Ricks",
            type = "Space station",
            dimension = "Unknown",
            residentIds = emptyList(),
        )
    }
}
