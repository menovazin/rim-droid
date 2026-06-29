package com.rim.droid.data.util

import com.rim.droid.domain.util.Result

/**
 * Wraps a suspending API call, converting any thrown exception into [Result.Error].
 */
suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
    Result.Success(apiCall())
} catch (e: Exception) {
    Result.Error(e)
}
