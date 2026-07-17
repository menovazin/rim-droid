package com.rim.droid.data.util

import com.rim.droid.domain.util.Result
import kotlinx.coroutines.CancellationException

/**
 * Wraps a suspending API call, converting any thrown exception into [Result.Error].
 * [CancellationException] is rethrown so coroutine cancellation is not swallowed.
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
    Result.Success(apiCall())
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Result.Error(e)
}
