package com.rim.droid.presentation.ui.episodes

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rim.droid.presentation.ui.common.CharacterAvatarCircle
import com.rim.droid.presentation.util.season
import com.rim.droid.presentation.util.episodeNumber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailScreen(
    episodeId: Int,
    onBack: () -> Unit,
    viewModel: EpisodeDetailViewModel = hiltViewModel(),
) {
    val episode by viewModel.episode.collectAsStateWithLifecycle()
    var scale by remember { mutableFloatStateOf(1f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(episode?.name ?: "Загрузка...") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
            )
        },
    ) { paddingValues ->
        val ep = episode
        if (ep == null) {
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

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
                color = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row {
                        Text(
                            text = "S%02d".format(ep.episodeCode.season),
                            style = MaterialTheme.typography.displaySmall.copy(fontSize = 48.sp),
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "E%02d".format(ep.episodeCode.episodeNumber),
                            style = MaterialTheme.typography.displaySmall.copy(fontSize = 48.sp),
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                    Text(text = ep.name, style = MaterialTheme.typography.headlineMedium)
                    Text(
                        text = ep.airDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (ep.characterIds.isNotEmpty()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Персонажи", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp),
                    ) {
                        items(ep.characterIds) { characterId ->
                            CharacterAvatarCircle(characterId = characterId, name = "#$characterId")
                        }
                    }
                }
            }
        }
    }
}
