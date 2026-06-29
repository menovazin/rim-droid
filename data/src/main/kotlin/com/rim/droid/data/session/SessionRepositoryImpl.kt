package com.rim.droid.data.session

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.rim.droid.domain.repository.SessionRepository
import javax.inject.Inject

/**
 * Fake session token persisted in an encrypted shared preferences file.
 *
 * EncryptedSharedPreferences requires API 23+; our minSdk is 26 so this is safe.
 */
class SessionRepositoryImpl @Inject constructor(
    context: Context,
) : SessionRepository {

    private val prefs: SharedPreferences = try {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    } catch (e: Exception) {
        // Fallback to plain prefs if the encrypted file cannot be opened (e.g. after a backup
        // restore on a new device). The token is fake anyway, so we degrade gracefully.
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    override fun hasToken(): Boolean = getToken() != null

    override fun clear() {
        prefs.edit().remove(KEY_TOKEN).apply()
    }

    private companion object {
        const val FILE_NAME = "rim_session"
        const val KEY_TOKEN = "token"
    }
}
