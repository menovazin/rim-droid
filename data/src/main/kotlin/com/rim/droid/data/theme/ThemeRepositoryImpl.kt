package com.rim.droid.data.theme

import android.content.Context
import android.content.SharedPreferences
import com.rim.droid.domain.entity.ThemeType
import com.rim.droid.domain.repository.ThemeRepository
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    context: Context,
) : ThemeRepository {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    override fun getTheme(): ThemeType {
        val raw = prefs.getString(KEY_THEME, null)
        return ThemeType.fromString(raw)
    }

    override fun setTheme(type: ThemeType) {
        prefs.edit().putString(KEY_THEME, type.serialize()).apply()
    }

    private companion object {
        const val FILE_NAME = "rim_settings"
        const val KEY_THEME = "theme"
    }
}
