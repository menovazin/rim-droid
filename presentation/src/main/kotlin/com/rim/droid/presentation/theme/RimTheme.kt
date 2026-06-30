package com.rim.droid.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

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

@Composable
fun RimTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = RimTypography,
        content = content,
    )
}