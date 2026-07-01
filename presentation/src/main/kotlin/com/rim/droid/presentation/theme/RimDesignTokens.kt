package com.rim.droid.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Semantic design tokens ported from Flutter CustomDesigns.
 * Provides branded colors and gradients for the Rick & Morty app.
 */
@Immutable
data class RimDesignTokens(
    val primary: Color,
    val secondary: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textDisabled: Color,
    val gradientButton: Brush,
    val gradientInactiveButton: Brush,
    val gradientAppBar: Brush,
) {
    companion object {
        fun dark(): RimDesignTokens {
            val b = RimBaseColors
            return RimDesignTokens(
                primary = b.sunsetPeach,
                secondary = b.spicedBrick,
                background = b.abyssBlack,
                surface = b.burntMerlot,
                error = b.error,
                onPrimary = b.black,
                onSecondary = b.vanillaCream,
                onBackground = b.vanillaCream,
                onSurface = b.vanillaCream,
                onError = b.vanillaCream,
                textPrimary = b.vanillaCream,
                textSecondary = b.white,
                textDisabled = b.white.copy(alpha = 0.5f),
                gradientButton = Brush.horizontalGradient(
                    colors = listOf(b.sunsetPeach, b.spicedBrick),
                ),
                gradientInactiveButton = Brush.horizontalGradient(
                    colors = listOf(b.burntMerlot, b.abyssBlack),
                ),
                gradientAppBar = Brush.verticalGradient(
                    colors = listOf(b.black, b.abyssBlack),
                ),
            )
        }

        fun light(): RimDesignTokens {
            val b = RimBaseColors
            return RimDesignTokens(
                primary = Color(0xFF28C76F),
                secondary = b.spicedBrick,
                background = Color(0xFFF3F4F6),
                surface = Color(0xFFFFFFFF),
                error = b.error,
                onPrimary = Color(0xFFFFFFFF),
                onSecondary = Color(0xFFFFFFFF),
                onBackground = b.abyssBlack,
                onSurface = b.abyssBlack,
                onError = Color(0xFFFFFFFF),
                textPrimary = b.abyssBlack,
                textSecondary = Color(0xFF6B7280),
                textDisabled = Color(0xFF9CA3AF),
                gradientButton = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF28C76F), b.spicedBrick),
                ),
                gradientInactiveButton = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFE5E7EB), Color(0xFFD1D5DB)),
                ),
                gradientAppBar = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF)),
                ),
            )
        }
    }
}

internal val LocalRimDesignTokens = staticCompositionLocalOf { RimDesignTokens.dark() }

/**
 * Short accessor for [RimDesignTokens] from any Composable.
 *
 * Usage: `MaterialTheme.rimColors.primary`
 */
val MaterialTheme.rimColors: RimDesignTokens
    @Composable get() = LocalRimDesignTokens.current
