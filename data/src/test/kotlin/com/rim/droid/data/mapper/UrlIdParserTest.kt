package com.rim.droid.data.mapper

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UrlIdParserTest {

    @Test
    fun `trailingIdOrNull extracts id from valid URL`() {
        assertThat("https://rickandmortyapi.com/api/episode/12".trailingIdOrNull()).isEqualTo(12)
    }

    @Test
    fun `trailingIdOrNull returns null for invalid URL`() {
        assertThat("not-a-url".trailingIdOrNull()).isNull()
    }

    @Test
    fun `toIds converts list of URLs to list of ids`() {
        val urls = listOf(
            "https://rickandmortyapi.com/api/character/1",
            "https://rickandmortyapi.com/api/character/42",
        )
        assertThat(urls.toIds()).containsExactly(1, 42)
    }

    @Test
    fun `toIds filters out invalid URLs`() {
        val urls = listOf("https://api.com/1", "invalid", "https://api.com/3")
        assertThat(urls.toIds()).containsExactly(1, 3)
    }
}
