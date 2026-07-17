package com.rim.droid.data.mapper

import com.rim.droid.data.BuildConfig
import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.dto.CharacterDto
import com.rim.droid.data.dto.NamedRefDto
import org.junit.Test

/**
 * spec: rim-rest-data-layer / Character mapping
 */
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
            origin = NamedRefDto("Earth", "${BuildConfig.BASE_URL}location/1"),
            location = NamedRefDto("Citadel of Ricks", "${BuildConfig.BASE_URL}location/3"),
            episode = listOf(
                "${BuildConfig.BASE_URL}episode/1",
                "${BuildConfig.BASE_URL}episode/2",
            ),
        )
        val domain = dto.toDomain()
        assertThat(domain.id).isEqualTo(1)
        assertThat(domain.name).isEqualTo("Rick Sanchez")
        assertThat(domain.image).isEqualTo("https://example.com/1.jpeg")
        assertThat(domain.origin).isEqualTo("Earth")
        assertThat(domain.location).isEqualTo("Citadel of Ricks")
        assertThat(domain.episodeIds).containsExactly(1, 2)
    }

    @Test
    fun `toDomain makes relative image URL absolute`() {
        val dto = CharacterDto(
            id = 2,
            name = "Test",
            image = "/character/avatar/2.jpeg",
        )
        assertThat(dto.toDomain().image).isEqualTo("${BuildConfig.BASE_URL}character/avatar/2.jpeg")
    }

    @Test
    fun `toDomain handles empty episode list`() {
        val dto = CharacterDto(id = 3, name = "Test", episode = emptyList())
        assertThat(dto.toDomain().episodeIds).isEmpty()
    }

    @Test
    fun `toDomain filters malformed episode urls`() {
        val dto = CharacterDto(
            id = 4,
            name = "Test",
            episode = listOf(
                "${BuildConfig.BASE_URL}episode/7",
                "not-a-url",
                "${BuildConfig.BASE_URL}episode/9",
            ),
        )
        assertThat(dto.toDomain().episodeIds).containsExactly(7, 9)
    }

    @Test
    fun `toDomain uses empty strings for missing origin and location`() {
        val dto = CharacterDto(
            id = 5,
            name = "Test",
            origin = NamedRefDto(name = ""),
            location = NamedRefDto(name = ""),
        )
        val domain = dto.toDomain()
        assertThat(domain.origin).isEmpty()
        assertThat(domain.location).isEmpty()
    }
}
