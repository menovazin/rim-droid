package com.rim.droid.presentation.util

import com.google.common.truth.Truth.assertThat
import com.rim.droid.domain.entity.Character
import com.rim.droid.presentation.theme.RimBaseColors
import org.junit.Test

/**
 * spec: test-strategy-v1 / Presentation utility unit tests
 */
class CharacterStatusTest {

    @Test
    fun `alive returns portal green`() {
        assertThat("Alive".statusColor()).isEqualTo(RimBaseColors.sunsetPeach)
        assertThat(Character(id = 1, name = "", status = "Alive").statusColor())
            .isEqualTo(RimBaseColors.sunsetPeach)
    }

    @Test
    fun `dead returns error red`() {
        assertThat("Dead".statusColor()).isEqualTo(RimBaseColors.error)
        assertThat(Character(id = 1, name = "", status = "Dead").statusColor())
            .isEqualTo(RimBaseColors.error)
    }

    @Test
    fun `unknown status returns muted white`() {
        assertThat("unknown".statusColor()).isEqualTo(RimBaseColors.white)
    }

    @Test
    fun `empty status returns muted white`() {
        assertThat("".statusColor()).isEqualTo(RimBaseColors.white)
    }

    @Test
    fun `mixed case alive is handled`() {
        assertThat("ALIVE".statusColor()).isEqualTo(RimBaseColors.sunsetPeach)
    }
}

private fun Character(
    id: Int,
    name: String,
    status: String,
) = Character(
    id = id,
    name = name,
    status = status,
    species = "",
    type = "",
    gender = "",
    image = "",
    origin = "",
    location = "",
    episodeIds = emptyList(),
)
