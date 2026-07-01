package com.rim.droid.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.rim.droid.domain.entity.ThemeType

private val DarkColors = darkColorScheme(
    primary = RimBaseColors.sunsetPeach,
    secondary = RimBaseColors.spicedBrick,
    background = RimBaseColors.abyssBlack,
    surface = RimBaseColors.burntMerlot,
    error = RimBaseColors.error,
    onPrimary = RimBaseColors.black,
    onSecondary = RimBaseColors.vanillaCream,
    onBackground = RimBaseColors.vanillaCream,
    onSurface = RimBaseColors.vanillaCream,
    onError = RimBaseColors.vanillaCream,
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF28C76F),
    secondary = RimBaseColors.spicedBrick,
    background = Color(0xFFF3F4F6),
    surface = Color(0xFFFFFFFF),
    error = RimBaseColors.error,
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onBackground = RimBaseColors.abyssBlack,
    onSurface = RimBaseColors.abyssBlack,
    onError = Color(0xFFFFFFFF),
)

@Composable
fun RimTheme(
    themeType: ThemeType = ThemeType.DARK,
    content: @Composable () -> Unit,
) {
    val isDark = when (themeType) {
        ThemeType.LIGHT -> false
        ThemeType.DARK -> true
        ThemeType.SYSTEM -> isSystemInDarkTheme()
    }
    val colorScheme = if (isDark) DarkColors else LightColors
    val designTokens = if (isDark) RimDesignTokens.dark() else RimDesignTokens.light()

    CompositionLocalProvider(LocalRimDesignTokens provides designTokens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = RimTypography,
            content = content,
        )
    }
}
