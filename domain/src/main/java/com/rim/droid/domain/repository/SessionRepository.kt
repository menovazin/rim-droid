package com.rim.droid.domain.repository

/**
 * Abstraction over the (fake) session token persistence.
 */
interface SessionRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun hasToken(): Boolean
    fun clear()
}
