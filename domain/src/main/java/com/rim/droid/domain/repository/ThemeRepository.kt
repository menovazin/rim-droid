package com.rim.droid.domain.repository

import com.rim.droid.domain.entity.ThemeType

interface ThemeRepository {
    fun getTheme(): ThemeType
    fun setTheme(type: ThemeType)
}
