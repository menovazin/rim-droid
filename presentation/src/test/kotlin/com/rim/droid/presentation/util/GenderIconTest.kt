package com.rim.droid.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Transgender
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * spec: test-strategy-v1 / Presentation utility unit tests
 */
class GenderIconTest {

    @Test
    fun `male returns male icon`() {
        assertThat("Male".genderIcon()).isSameInstanceAs(Icons.Default.Male)
        assertThat("male".genderSymbol()).isEqualTo("♂")
    }

    @Test
    fun `female returns female icon`() {
        assertThat("Female".genderIcon()).isSameInstanceAs(Icons.Default.Female)
        assertThat("female".genderSymbol()).isEqualTo("♀")
    }

    @Test
    fun `genderless returns transgender icon`() {
        assertThat("Genderless".genderIcon()).isSameInstanceAs(Icons.Default.Transgender)
        assertThat("genderless".genderSymbol()).isEqualTo("⚪")
    }

    @Test
    fun `unknown gender returns question mark`() {
        assertThat("unknown".genderIcon()).isSameInstanceAs(Icons.Default.QuestionMark)
        assertThat("unknown".genderSymbol()).isEqualTo("?")
    }

    @Test
    fun `empty gender returns question mark`() {
        assertThat("".genderIcon()).isSameInstanceAs(Icons.Default.QuestionMark)
        assertThat("".genderSymbol()).isEqualTo("?")
    }
}
