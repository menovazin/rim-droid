package com.rim.droid.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.rim.droid.presentation.theme.rimColors

/**
 * Circular character avatar with optional label under it.
 */
@Composable
fun CharacterAvatarCircle(
    characterId: Int,
    name: String?,
    modifier: Modifier = Modifier,
) {
    val rimColors = MaterialTheme.rimColors
    val avatarUrl = LocalAvatarUrlProvider.current.fromCharacterId(characterId)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(64.dp),
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(rimColors.surface),
            contentAlignment = Alignment.Center,
        ) {
            SubcomposeAsyncImage(
                model = avatarUrl,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(48.dp),
                error = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = rimColors.textSecondary,
                        modifier = Modifier.size(24.dp),
                    )
                },
            )
        }
        if (name != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = rimColors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}
