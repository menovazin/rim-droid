package com.rim.droid.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rim.droid.R

/**
 * Nunito font family matching the Flutter `pubspec.yaml` fonts section.
 * 9 weights mapped to 8 TTF resources (ExtraLight covers both Thin 100 and ExtraLight 200).
 */
val NunitoFontFamily = FontFamily(
    Font(R.font.nunito_extralight, weight = FontWeight.Thin),
    Font(R.font.nunito_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.nunito_light, weight = FontWeight.Light),
    Font(R.font.nunito_regular, weight = FontWeight.Normal),
    Font(R.font.nunito_medium, weight = FontWeight.Medium),
    Font(R.font.nunito_semibold, weight = FontWeight.SemiBold),
    Font(R.font.nunito_bold, weight = FontWeight.Bold),
    Font(R.font.nunito_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.nunito_black, weight = FontWeight.Black),
)

/**
 * Typography matching the Flutter TextTheme in arch-refs/rim-flutter/lib/themes/dark/dark_theme_data.dart.
 * Uses [RimDesignTokens.textPrimary] as the default color — applied via RimTheme.
 * All styles use [NunitoFontFamily] to match the Flutter Nunito typeface.
 */
val RimTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp,
    ),
    displaySmall = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 48.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        letterSpacing = 1.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
    ),
)
