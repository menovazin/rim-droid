package com.rim.droid.data.mapper

import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.dto.LocationDto
import org.junit.Test

class LocationMapperTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        val dto = LocationDto(
            id = 1,
            name = "Earth",
            type = "Planet",
            dimension = "Dimension C-137",
            residents = listOf(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2",
            ),
        )
        val domain = dto.toDomain()
        assertThat(domain.id).isEqualTo(1)
        assertThat(domain.name).isEqualTo("Earth")
        assertThat(domain.type).isEqualTo("Planet")
        assertThat(domain.dimension).isEqualTo("Dimension C-137")
        assertThat(domain.residentIds).containsExactly(1, 2)
    }
}
