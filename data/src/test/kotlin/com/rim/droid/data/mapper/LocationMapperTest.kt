package com.rim.droid.data.mapper

import com.rim.droid.data.BuildConfig
import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.dto.LocationDto
import org.junit.Test

/**
 * spec: rim-rest-data-layer / Location mapping
 */
class LocationMapperTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        val dto = LocationDto(
            id = 1,
            name = "Earth",
            type = "Planet",
            dimension = "Dimension C-137",
            residents = listOf(
                "${BuildConfig.BASE_URL}character/1",
                "${BuildConfig.BASE_URL}character/2",
            ),
        )
        val domain = dto.toDomain()
        assertThat(domain.id).isEqualTo(1)
        assertThat(domain.name).isEqualTo("Earth")
        assertThat(domain.type).isEqualTo("Planet")
        assertThat(domain.dimension).isEqualTo("Dimension C-137")
        assertThat(domain.residentIds).containsExactly(1, 2)
    }

    @Test
    fun `toDomain handles empty resident list`() {
        val dto = LocationDto(id = 2, name = "Test", residents = emptyList())
        assertThat(dto.toDomain().residentIds).isEmpty()
    }

    @Test
    fun `toDomain filters malformed resident urls`() {
        val dto = LocationDto(
            id = 3,
            name = "Test",
            residents = listOf(
                "${BuildConfig.BASE_URL}character/3",
                "bad-url",
                "${BuildConfig.BASE_URL}character/4",
            ),
        )
        assertThat(dto.toDomain().residentIds).containsExactly(3, 4)
    }
}
