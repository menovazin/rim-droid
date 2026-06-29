package com.rim.droid.data.mapper

import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.dto.EpisodeDto
import org.junit.Test

class EpisodeMapperTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        val dto = EpisodeDto(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            characters = listOf(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2",
            ),
        )
        val domain = dto.toDomain()
        assertThat(domain.id).isEqualTo(1)
        assertThat(domain.name).isEqualTo("Pilot")
        assertThat(domain.episodeCode).isEqualTo("S01E01")
        assertThat(domain.characterIds).containsExactly(1, 2)
    }
}
