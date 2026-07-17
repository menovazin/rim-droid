package com.rim.droid.presentation

import android.content.res.Configuration
import android.graphics.Color as AndroidColor
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    // Bind system bars to app theme (not system night mode). enableEdgeToEdge()
    // defaults use Configuration.UI_MODE_NIGHT, so a forced DARK app on a light
    // system would otherwise keep a light navigation bar.
    SideEffect {
        val activity = context as? ComponentActivity ?: return@SideEffect
        val statusBarStyle = if (isLight) {
            SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT)
        } else {
            SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        }
        val navigationBarStyle = if (isLight) {
            SystemBarStyle.light(AndroidColor.TRANSPARENT, AndroidColor.TRANSPARENT)
        } else {
            SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        }
        activity.enableEdgeToEdge(
            statusBarStyle = statusBarStyle,
            navigationBarStyle = navigationBarStyle,
        )
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
