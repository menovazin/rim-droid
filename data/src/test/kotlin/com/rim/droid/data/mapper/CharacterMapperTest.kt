package com.rim.droid.data.mapper

import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.dto.CharacterDto
import com.rim.droid.data.dto.NamedRefDto
import org.junit.Test

class CharacterMapperTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        val dto = CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://example.com/1.jpeg",
            origin = NamedRefDto("Earth", "https://rickandmortyapi.com/api/location/1"),
            location = NamedRefDto("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3"),
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
            ),
        )
        val domain = dto.toDomain()
        assertThat(domain.id).isEqualTo(1)
        assertThat(domain.name).isEqualTo("Rick Sanchez")
        assertThat(domain.origin).isEqualTo("Earth")
        assertThat(domain.location).isEqualTo("Citadel of Ricks")
        assertThat(domain.episodeIds).containsExactly(1, 2)
    }

    @Test
    fun `toDomain handles empty episode list`() {
        val dto = CharacterDto(id = 2, name = "Test", episode = emptyList())
        assertThat(dto.toDomain().episodeIds).isEmpty()
    }
}
