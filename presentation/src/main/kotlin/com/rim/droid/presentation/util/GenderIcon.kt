package com.rim.droid.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.ui.graphics.vector.ImageVector

fun String.genderIcon(): ImageVector = when (this.lowercase()) {
    "male" -> Icons.Default.Male
    "female" -> Icons.Default.Female
    "genderless" -> Icons.Default.Transgender
    else -> Icons.Default.QuestionMark
}

fun String.genderSymbol(): String = when (this.lowercase()) {
    "male" -> "♂"
    "female" -> "♀"
    "genderless" -> "⚪"
    else -> "?"
}
