package com.rim.droid.presentation.util

import androidx.compose.ui.graphics.Color
import com.rim.droid.domain.entity.Character
import com.rim.droid.presentation.theme.RimBaseColors

/**
 * Status indicator color: green (Alive), red (Dead), grey (unknown).
 * Ported from Flutter `character_status_x.dart`.
 */
fun Character.statusColor(): Color = status.statusColor()

fun String.statusColor(): Color = when (this.lowercase()) {
    "alive" -> RimBaseColors.sunsetPeach
    "dead" -> RimBaseColors.error
    else -> RimBaseColors.white
}
