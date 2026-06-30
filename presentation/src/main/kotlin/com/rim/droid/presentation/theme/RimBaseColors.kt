package com.rim.droid.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * Rick & Morty base palette, ported from Flutter BaseDesigns.
 * All values correspond to the oklch→sRGB conversion in arch-refs/rim-flutter.
 */
object RimBaseColors {
    /** Near-white green — foreground / textPrimary */
    val vanillaCream = Color(0xFFEAF6EC)

    /** Portal green — primary */
    val sunsetPeach = Color(0xFF34E27A)

    /** Neon purple — secondary */
    val spicedBrick = Color(0xFF9B53D6)

    /** Dark teal card — surface */
    val burntMerlot = Color(0xFF16272B)

    /** Deep teal — background */
    val abyssBlack = Color(0xFF0E1B1F)

    /** Destructive red */
    val error = Color(0xFFE5484D)

    /** Darkest teal — sidebar */
    val black = Color(0xFF0B1618)

    /** Muted foreground */
    val white = Color(0xFF9DB5B1)
}
