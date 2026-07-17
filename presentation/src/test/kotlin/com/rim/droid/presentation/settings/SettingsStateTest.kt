package com.rim.droid.presentation.settings

import com.google.common.truth.Truth.assertThat
import com.rim.droid.domain.entity.ThemeType
import com.rim.droid.domain.repository.ThemeRepository
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * spec: theme-selection / Theme switching
 */
class SettingsStateTest {

    private lateinit var repository: ThemeRepository
    private lateinit var state: SettingsState

    @Before
    fun setUp() {
        repository = mock()
    }

    @Test
    fun `initial value from repository`() {
        whenever(repository.getTheme()).thenReturn(ThemeType.DARK)
        state = SettingsState(repository)
        assertThat(state.themeType.value).isEqualTo(ThemeType.DARK)
    }

    @Test
    fun `initial value defaults to system when repository returns system`() {
        whenever(repository.getTheme()).thenReturn(ThemeType.SYSTEM)
        state = SettingsState(repository)
        assertThat(state.themeType.value).isEqualTo(ThemeType.SYSTEM)
    }

    @Test
    fun `toggleTheme dark to light`() {
        whenever(repository.getTheme()).thenReturn(ThemeType.DARK)
        state = SettingsState(repository)
        state.toggleTheme()
        assertThat(state.themeType.value).isEqualTo(ThemeType.LIGHT)
        verify(repository).setTheme(ThemeType.LIGHT)
    }

    @Test
    fun `toggleTheme light to dark`() {
        whenever(repository.getTheme()).thenReturn(ThemeType.LIGHT)
        state = SettingsState(repository)
        state.toggleTheme()
        assertThat(state.themeType.value).isEqualTo(ThemeType.DARK)
        verify(repository).setTheme(ThemeType.DARK)
    }

    @Test
    fun `toggleTheme system to dark`() {
        whenever(repository.getTheme()).thenReturn(ThemeType.SYSTEM)
        state = SettingsState(repository)
        state.toggleTheme()
        assertThat(state.themeType.value).isEqualTo(ThemeType.DARK)
        verify(repository).setTheme(ThemeType.DARK)
    }

    @Test
    fun `setTheme persists`() {
        whenever(repository.getTheme()).thenReturn(ThemeType.SYSTEM)
        state = SettingsState(repository)
        state.setTheme(ThemeType.LIGHT)
        assertThat(state.themeType.value).isEqualTo(ThemeType.LIGHT)
        verify(repository).setTheme(ThemeType.LIGHT)
    }
}
