package com.rim.droid.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Tv
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * spec: test-strategy-v1 / Presentation utility unit tests
 */
class LocationTypeIconTest {

    @Test
    fun `planet returns public icon`() {
        assertThat("Planet".locationTypeIcon()).isSameInstanceAs(Icons.Default.Public)
    }

    @Test
    fun `space station returns rocket launch icon`() {
        assertThat("Space station".locationTypeIcon()).isSameInstanceAs(Icons.Default.RocketLaunch)
    }

    @Test
    fun `microverse returns grain icon`() {
        assertThat("Microverse".locationTypeIcon()).isSameInstanceAs(Icons.Default.Grain)
    }

    @Test
    fun `dream returns cloud icon`() {
        assertThat("Dream".locationTypeIcon()).isSameInstanceAs(Icons.Default.Cloud)
    }

    @Test
    fun `tv returns tv icon`() {
        assertThat("TV".locationTypeIcon()).isSameInstanceAs(Icons.Default.Tv)
    }

    @Test
    fun `resort returns pool icon`() {
        assertThat("Resort".locationTypeIcon()).isSameInstanceAs(Icons.Default.Pool)
    }

    @Test
    fun `fantasy town returns castle icon`() {
        assertThat("Fantasy town".locationTypeIcon()).isSameInstanceAs(Icons.Default.Castle)
    }

    @Test
    fun `cluster returns bubble chart icon`() {
        assertThat("Cluster".locationTypeIcon()).isSameInstanceAs(Icons.Default.BubbleChart)
    }

    @Test
    fun `unknown type returns default location icon`() {
        assertThat("unknown".locationTypeIcon()).isSameInstanceAs(Icons.Default.LocationOn)
    }

    @Test
    fun `empty type returns default location icon`() {
        assertThat("".locationTypeIcon()).isSameInstanceAs(Icons.Default.LocationOn)
    }
}
