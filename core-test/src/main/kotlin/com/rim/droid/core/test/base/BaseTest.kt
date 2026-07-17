package com.rim.droid.core.test.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rim.droid.core.test.rules.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule

/**
 * Base class for JVM unit tests that need deterministic coroutine dispatchers
 * and an immediate `LiveData`/`StateFlow` executor.
 */
@OptIn(ExperimentalCoroutinesApi::class)
open class BaseTest {

    private val unconfinedTestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule(testDispatcher = unconfinedTestDispatcher)

    fun runUnconfinedTest(block: suspend TestScope.() -> Unit) = runTest(
        context = unconfinedTestDispatcher,
        testBody = block,
    )
}
