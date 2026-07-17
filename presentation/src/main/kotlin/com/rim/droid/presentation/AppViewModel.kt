package com.rim.droid.presentation

import androidx.lifecycle.ViewModel
import com.rim.droid.domain.entity.ThemeType
import com.rim.droid.domain.repository.AvatarUrlProvider
import com.rim.droid.domain.repository.SessionRepository
import com.rim.droid.presentation.settings.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * App-scoped owner for session start destination, logout, theme, and avatar URL resolution.
 * Keeps [MainActivity] free of repository/settings APIs.
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val settingsState: SettingsState,
    val avatarUrlProvider: AvatarUrlProvider,
) : ViewModel() {

    val themeType: StateFlow<ThemeType> = settingsState.themeType

    fun hasToken(): Boolean = sessionRepository.hasToken()

    fun logout() {
        sessionRepository.clear()
    }

    fun toggleTheme() {
        settingsState.toggleTheme()
    }
}
