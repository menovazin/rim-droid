package com.rim.droid.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import com.rim.droid.R
import com.rim.droid.domain.entity.ThemeType
import com.rim.droid.domain.repository.SessionRepository
import com.rim.droid.presentation.navigation.RimNavHost
import com.rim.droid.presentation.settings.SettingsState
import com.rim.droid.presentation.theme.RimTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionRepository: SessionRepository

    @Inject
    lateinit var settingsState: SettingsState

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RIM)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeType = settingsState.themeType.collectAsState().value
            val isLight = isLightTheme(themeType)
            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView)
                    .isAppearanceLightStatusBars = isLight
            }
            RimTheme(themeType = themeType) {
                RimNavHost(
                    hasToken = sessionRepository.hasToken(),
                    onLogout = { sessionRepository.clear() },
                    onToggleTheme = { settingsState.toggleTheme() },
                )
            }
        }
    }

    private fun isLightTheme(themeType: ThemeType): Boolean = when (themeType) {
        ThemeType.LIGHT -> true
        ThemeType.DARK -> false
        ThemeType.SYSTEM -> {
            val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            nightMode != Configuration.UI_MODE_NIGHT_YES
        }
    }
}
