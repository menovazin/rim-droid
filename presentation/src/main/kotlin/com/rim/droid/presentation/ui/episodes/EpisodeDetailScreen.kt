package com.rim.droid.presentation.ui.episodes

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    var scale by remember { mutableFloatStateOf(1f) }

    Scaffold(
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
                    // Пустая иконка-заглушка для симметрии с navigationIcon,
                    // чтобы заголовок визуально центрировался.
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = Color.Transparent,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.rimColors.background,
                    titleContentColor = MaterialTheme.rimColors.textPrimary,
                    navigationIconContentColor = MaterialTheme.rimColors.textPrimary,
                    actionIconContentColor = MaterialTheme.rimColors.textPrimary,
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
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.rimColors.surface,
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row {
                        Text(
                            text = "S%02d".format(episode.episodeCode.season),
                            style = MaterialTheme.typography.displaySmall.copy(fontSize = 48.sp),
                            color = MaterialTheme.rimColors.primary,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "E%02d".format(episode.episodeCode.episodeNumber),
                            style = MaterialTheme.typography.displaySmall.copy(fontSize = 48.sp),
                            color = MaterialTheme.rimColors.secondary,
                        )
                    }
                    Text(text = episode.name, style = MaterialTheme.typography.headlineMedium)
                    Text(
                        text = episode.airDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.rimColors.textSecondary,
                    )
                }
            }

            if (episode.characterIds.isNotEmpty()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Персонажи", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
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
