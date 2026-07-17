package com.rim.droid.data.util

import com.google.common.truth.Truth.assertThat
import com.rim.droid.domain.util.Result
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

/**
 * spec: rim-rest-data-layer / Network error handling
 */
class SafeApiCallTest {

    @Test
    fun `safeApiCall returns Success for value`() = runTest {
        val result = safeApiCall { "ok" }
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isEqualTo("ok")
    }

    @Test
    fun `safeApiCall returns Error with IOException`() = runTest {
        val thrown = IOException("boom")
        val result = safeApiCall { throw thrown }
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isSameInstanceAs(thrown)
    }

    @Test
    fun `safeApiCall preserves RuntimeException subclass`() = runTest {
        val thrown = IllegalStateException("x")
        val result = safeApiCall { throw thrown }
        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isInstanceOf(IllegalStateException::class.java)
        assertThat(result.error).isSameInstanceAs(thrown)
    }
}
