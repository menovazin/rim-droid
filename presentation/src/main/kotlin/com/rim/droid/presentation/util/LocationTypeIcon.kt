package com.rim.droid.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector

fun String.locationTypeIcon(): ImageVector = when (this.lowercase()) {
    "planet" -> Icons.Default.Public
    "space station" -> Icons.Default.RocketLaunch
    "microverse" -> Icons.Default.Grain
    "dream" -> Icons.Default.Cloud
    "tv" -> Icons.Default.Tv
    "resort" -> Icons.Default.Pool
    "fantasy town" -> Icons.Default.Castle
    "cluster" -> Icons.Default.BubbleChart
    else -> Icons.Default.LocationOn
}
