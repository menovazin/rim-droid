package com.rim.droid.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Typography matching the Flutter TextTheme in arch-refs/rim-flutter/lib/themes/dark/dark_theme_data.dart.
 * Uses [RimDesignTokens.textPrimary] as the default color — applied via RimTheme.
 */
val RimTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp,
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp,
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 48.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        letterSpacing = 1.5.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.W600,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
    ),
)
