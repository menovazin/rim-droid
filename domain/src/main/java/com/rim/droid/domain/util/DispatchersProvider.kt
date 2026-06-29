package com.rim.droid.domain.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Abstraction over coroutine dispatchers to keep the domain layer testable.
 */
interface DispatchersProvider {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}
