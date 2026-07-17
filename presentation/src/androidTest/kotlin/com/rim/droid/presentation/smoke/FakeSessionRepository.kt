package com.rim.droid.presentation.smoke

import com.rim.droid.domain.repository.SessionRepository

class FakeSessionRepository : SessionRepository {
    @Volatile
    private var token: String? = null

    override fun saveToken(token: String) {
        this.token = token
    }

    override fun getToken(): String? = token

    override fun hasToken(): Boolean = !token.isNullOrBlank()

    override fun clear() {
        token = null
    }
}
