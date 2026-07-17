package com.rim.droid.data.mapper

import com.rim.droid.data.BuildConfig
import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.dto.EpisodeDto
import org.junit.Test

/**
 * spec: rim-rest-data-layer / Episode mapping
 */
class EpisodeMapperTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        val dto = EpisodeDto(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            characters = listOf(
                "${BuildConfig.BASE_URL}character/1",
                "${BuildConfig.BASE_URL}character/2",
            ),
        )
        val domain = dto.toDomain()
        assertThat(domain.id).isEqualTo(1)
        assertThat(domain.name).isEqualTo("Pilot")
        assertThat(domain.episodeCode).isEqualTo("S01E01")
        assertThat(domain.characterIds).containsExactly(1, 2)
    }

    @Test
    fun `toDomain handles empty character list`() {
        val dto = EpisodeDto(id = 2, name = "Test", characters = emptyList())
        assertThat(dto.toDomain().characterIds).isEmpty()
    }

    @Test
    fun `toDomain filters malformed character urls`() {
        val dto = EpisodeDto(
            id = 3,
            name = "Test",
            characters = listOf(
                "${BuildConfig.BASE_URL}character/5",
                "not-a-url",
            ),
        )
        assertThat(dto.toDomain().characterIds).containsExactly(5)
    }
}
