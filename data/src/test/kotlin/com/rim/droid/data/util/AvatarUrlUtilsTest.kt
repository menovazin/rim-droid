package com.rim.droid.data.util

import com.google.common.truth.Truth.assertThat
import com.rim.droid.data.BuildConfig
import org.junit.Test

class AvatarUrlUtilsTest {

    @Test
    fun `getCustomAvatarUrl returns absolute URL unchanged`() {
        assertThat(AvatarUrlUtils.getCustomAvatarUrl("https://example.com/1.jpeg"))
            .isEqualTo("https://example.com/1.jpeg")
    }

    @Test
    fun `getCustomAvatarUrl prepends base URL to relative path`() {
        assertThat(AvatarUrlUtils.getCustomAvatarUrl("/character/avatar/1.jpeg"))
            .isEqualTo("${BuildConfig.BASE_URL}character/avatar/1.jpeg")
    }

    @Test
    fun `avatarUrlFromId builds avatar URL from id`() {
        assertThat(AvatarUrlUtils.avatarUrlFromId(42))
            .isEqualTo("${BuildConfig.BASE_URL}character/avatar/42.jpeg")
    }
}
