package com.rim.droid.presentation.ui.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rim.droid.domain.entity.Episode
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.util.season
import com.rim.droid.presentation.util.episodeNumber

@Composable
fun EpisodesScreen(
    viewModel: EpisodesViewModel,
    onEpisodeClick: (Episode) -> Unit,
    modifier: Modifier = Modifier,
) {
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    val rimColors = MaterialTheme.rimColors

    Box(modifier = modifier.fillMaxSize()) {
        when {
            episodes.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = rimColors.primary,
                )
            }
            episodes.loadState.refresh is LoadState.Error -> {
                val error = (episodes.loadState.refresh as LoadState.Error).error
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Ошибка: ${error.localizedMessage}",
                        color = rimColors.textSecondary,
                    )
                    Button(
                        onClick = { episodes.retry() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = rimColors.primary,
                            contentColor = rimColors.onPrimary,
                        ),
                    ) {
                        Text("Повторить")
                    }
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(episodes.itemCount) { index ->
                        episodes[index]?.let { episode ->
                            EpisodeCard(
                                episode = episode,
                                onClick = { onEpisodeClick(episode) },
                            )
                        }
                    }
                    if (episodes.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(color = rimColors.primary)
                            }
                        }
                    }
                    if (episodes.loadState.append is LoadState.Error) {
                        item {
                            Button(
                                onClick = { episodes.retry() },
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = rimColors.primary,
                                    contentColor = rimColors.onPrimary,
                                ),
                            ) {
                                Text("Повторить загрузку")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EpisodeCard(
    episode: Episode,
    onClick: () -> Unit,
) {
    val rimColors = MaterialTheme.rimColors
    val s = episode.episodeCode.season
    val e = episode.episodeCode.episodeNumber

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(rimColors.surface)
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(rimColors.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "S${s.toString().padStart(2, '0')}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = rimColors.primary,
                            fontWeight = FontWeight.W700,
                        ),
                    )
                    Text(
                        text = "E${e.toString().padStart(2, '0')}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = rimColors.primary.copy(alpha = 0.6f),
                            fontWeight = FontWeight.W600,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp,
                        ),
                    )
                }
            }
            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                Text(
                    text = episode.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.W700,
                    color = rimColors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = episode.airDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = rimColors.textSecondary,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = rimColors.textSecondary,
            )
        }
    }
}
