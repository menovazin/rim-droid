package com.rim.droid.presentation

import com.google.common.truth.Truth.assertThat
import com.rim.droid.core.test.base.BaseTest
import com.rim.droid.domain.entity.ThemeType
import com.rim.droid.domain.repository.AvatarUrlProvider
import com.rim.droid.domain.repository.SessionRepository
import com.rim.droid.domain.repository.ThemeRepository
import com.rim.droid.presentation.settings.SettingsState
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AppViewModelTest : BaseTest() {

    private val sessionRepository: SessionRepository = mock()
    private val themeRepository: ThemeRepository = mock()
    private val avatarUrlProvider: AvatarUrlProvider = mock()
    private lateinit var settingsState: SettingsState
    private lateinit var viewModel: AppViewModel

    @Before
    fun setUp() {
        whenever(themeRepository.getTheme()).thenReturn(ThemeType.LIGHT)
        settingsState = SettingsState(themeRepository)
        viewModel = AppViewModel(sessionRepository, settingsState, avatarUrlProvider)
    }

    @Test
    fun `hasToken delegates to session repository`() {
        whenever(sessionRepository.hasToken()).thenReturn(true)
        assertThat(viewModel.hasToken()).isTrue()
        verify(sessionRepository).hasToken()
    }

    @Test
    fun `logout clears session`() {
        viewModel.logout()
        verify(sessionRepository).clear()
    }

    @Test
    fun `toggleTheme flips light to dark`() {
        assertThat(viewModel.themeType.value).isEqualTo(ThemeType.LIGHT)
        viewModel.toggleTheme()
        assertThat(viewModel.themeType.value).isEqualTo(ThemeType.DARK)
        verify(themeRepository).setTheme(ThemeType.DARK)
    }
}
