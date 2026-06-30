package com.rim.droid.presentation.ui.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rim.droid.domain.entity.Episode
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.ui.common.CharacterAvatarCircle
import com.rim.droid.presentation.util.season
import com.rim.droid.presentation.util.episodeNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(
    episode: Episode,
    onBack: () -> Unit,
) {
    val rimColors = MaterialTheme.rimColors
    var scale by remember { mutableFloatStateOf(1f) }

    Scaffold(
        containerColor = rimColors.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = episode.name,
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = Color.Transparent,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = rimColors.background,
                    titleContentColor = rimColors.textPrimary,
                    navigationIconContentColor = rimColors.textPrimary,
                    actionIconContentColor = rimColors.textPrimary,
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        scale = (scale * zoom).coerceIn(0.5f, 3f)
                    }
                }
                .graphicsLayer(scaleX = scale, scaleY = scale),
        ) {
            val s = episode.episodeCode.season
            val e = episode.episodeCode.episodeNumber

            // Gradient card with badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                rimColors.primary.copy(alpha = 0.15f),
                                rimColors.surface,
                            ),
                        ),
                    )
                    .padding(20.dp),
            ) {
                Column {
                    Row {
                        // S01 badge (filled)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(rimColors.primary)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = "S%02d".format(s),
                                style = MaterialTheme.typography.labelLarge,
                                color = rimColors.onPrimary,
                                fontWeight = FontWeight.W700,
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        // E01 badge (outlined)
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = rimColors.primary.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(8.dp),
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = "E%02d".format(e),
                                style = MaterialTheme.typography.labelLarge,
                                color = rimColors.primary,
                                fontWeight = FontWeight.W700,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = episode.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = rimColors.textPrimary,
                        fontWeight = FontWeight.W700,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = episode.airDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = rimColors.textSecondary,
                    )
                }
            }

            // Characters section
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Персонажи (${episode.characterIds.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = rimColors.primary,
                    fontWeight = FontWeight.W700,
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (episode.characterIds.isEmpty()) {
                    Text(
                        text = "Нет персонажей",
                        style = MaterialTheme.typography.bodyMedium,
                        color = rimColors.textSecondary,
                    )
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp),
                    ) {
                        items(episode.characterIds) { characterId ->
                            CharacterAvatarCircle(characterId = characterId, name = "#$characterId")
                        }
                    }
                }
            }
        }
    }
}