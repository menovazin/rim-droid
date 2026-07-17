package com.rim.droid.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rim.droid.presentation.theme.rimColors

/**
 * A label/value row used on detail screens.
 * Ported from Flutter `DetailInfoRow`.
 */
@Composable
fun DetailInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    val rimColors = MaterialTheme.rimColors
    Row(modifier = modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = rimColors.textSecondary,
            modifier = Modifier.width(140.dp),
        )
        Text(
            text = value.ifEmpty { "—" },
            style = MaterialTheme.typography.bodyMedium,
            color = rimColors.textPrimary,
            fontWeight = FontWeight.W600,
            modifier = Modifier.weight(1f),
        )
    }
}

/**
 * Section header used on detail screens.
 * Ported from Flutter `DetailSectionTitle`.
 */
@Composable
fun DetailSectionTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    val rimColors = MaterialTheme.rimColors
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = rimColors.primary,
        fontWeight = FontWeight.W700,
        modifier = modifier,
    )
}

/**
 * A small chip used to render related entity ids (episodes / residents).
 * Ported from Flutter `DetailChip`.
 */
@Composable
fun DetailChip(
    label: String,
    modifier: Modifier = Modifier,
) {
    val rimColors = MaterialTheme.rimColors
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(rimColors.surface)
            .border(
                width = 1.dp,
                color = rimColors.secondary.copy(alpha = 0.4f),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = rimColors.textPrimary,
        )
    }
}
