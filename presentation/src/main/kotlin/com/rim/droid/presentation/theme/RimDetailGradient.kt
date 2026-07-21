package com.rim.droid.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

/**
 * Unpremultiplied multi-stop hero gradient for Episode / Location detail cards.
 * Episode accent = [RimDesignTokens.primary]; Location accent = [RimDesignTokens.secondary].
 */
fun rimDetailHeroGradient(
    accent: Color,
    surface: Color,
    background: Color,
): Brush {
    return Brush.linearGradient(
        colorStops = arrayOf(
            0f to rimDetailHeroGradientStop(0f, accent, surface, background),
            0.25f to rimDetailHeroGradientStop(0.25f, accent, surface, background),
            0.5f to rimDetailHeroGradientStop(0.5f, accent, surface, background),
            0.75f to rimDetailHeroGradientStop(0.75f, accent, surface, background),
            1f to surface,
        ),
    )
}

/**
 * Single gradient stop at progress [t] in `0..1`.
 *
 * Unpremultiplied lerp of accent→surface RGB, alpha = 0.15 + 0.85*t, then src-over
 * composite onto [background] so the stop is fully opaque.
 */
fun rimDetailHeroGradientStop(
    t: Float,
    accent: Color,
    surface: Color,
    background: Color,
): Color {
    val alpha = 0.15f + 0.85f * t
    val r = accent.red + (surface.red - accent.red) * t
    val g = accent.green + (surface.green - accent.green) * t
    val b = accent.blue + (surface.blue - accent.blue) * t
    return Color(r, g, b, alpha).compositeOver(background)
}
