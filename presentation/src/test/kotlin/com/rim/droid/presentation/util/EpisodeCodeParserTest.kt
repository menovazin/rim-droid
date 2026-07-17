package com.rim.droid.presentation.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * spec: test-strategy-v1 / Presentation utility unit tests
 */
class EpisodeCodeParserTest {

    @Test
    fun `season and episodeNumber parse S01E05`() {
        assertThat("S01E05".season).isEqualTo(1)
        assertThat("S01E05".episodeNumber).isEqualTo(5)
    }

    @Test
    fun `season and episodeNumber parse S10E42`() {
        assertThat("S10E42".season).isEqualTo(10)
        assertThat("S10E42".episodeNumber).isEqualTo(42)
    }

    @Test
    fun `malformed code returns zero`() {
        assertThat("xyz".season).isEqualTo(0)
        assertThat("xyz".episodeNumber).isEqualTo(0)
    }

    @Test
    fun `empty string returns zero`() {
        assertThat("".season).isEqualTo(0)
        assertThat("".episodeNumber).isEqualTo(0)
    }
}
