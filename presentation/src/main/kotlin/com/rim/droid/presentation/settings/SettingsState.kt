package com.rim.droid.presentation.settings

import com.rim.droid.domain.entity.ThemeType
import com.rim.droid.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsState @Inject constructor(
    private val themeRepository: ThemeRepository,
) {
    private val _themeType = MutableStateFlow(themeRepository.getTheme())
    val themeType: StateFlow<ThemeType> = _themeType.asStateFlow()

    fun setTheme(type: ThemeType) {
        _themeType.value = type
        themeRepository.setTheme(type)
    }

    fun toggleTheme() {
        val current = _themeType.value
        setTheme(if (current == ThemeType.DARK) ThemeType.LIGHT else ThemeType.DARK)
    }
}
