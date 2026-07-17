package com.rim.droid.presentation

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rim.droid.domain.entity.ThemeType
import com.rim.droid.presentation.navigation.RimNavHost
import com.rim.droid.presentation.theme.RimTheme
import com.rim.droid.presentation.ui.common.LocalAvatarUrlProvider

@Composable
fun RimRoot(
    appViewModel: AppViewModel = hiltViewModel(),
) {
    val themeType by appViewModel.themeType.collectAsStateWithLifecycle()
    val isLight = isLightTheme(themeType)
    val view = LocalView.current
    val context = LocalContext.current

    SideEffect {
        val window = (context as? Activity)?.window ?: return@SideEffect
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = isLight
    }

    CompositionLocalProvider(
        LocalAvatarUrlProvider provides appViewModel.avatarUrlProvider,
    ) {
        RimTheme(themeType = themeType) {
            RimNavHost(
                hasToken = appViewModel.hasToken(),
                onLogout = appViewModel::logout,
                onToggleTheme = appViewModel::toggleTheme,
            )
        }
    }
}

@Composable
private fun isLightTheme(themeType: ThemeType): Boolean {
    val context = LocalContext.current
    return when (themeType) {
        ThemeType.LIGHT -> true
        ThemeType.DARK -> false
        ThemeType.SYSTEM -> {
            val nightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            nightMode != Configuration.UI_MODE_NIGHT_YES
        }
    }
}
