package com.rim.droid.presentation.ui.episodes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rim.droid.domain.entity.Episode
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.util.season
import com.rim.droid.presentation.util.episodeNumber

@Composable
fun EpisodesScreen(
    viewModel: EpisodesViewModel,
    onEpisodeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            episodes.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            episodes.loadState.refresh is LoadState.Error -> {
                val error = (episodes.loadState.refresh as LoadState.Error).error
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Ошибка: ${error.localizedMessage}")
                    Button(onClick = { episodes.retry() }) {
                        Text("Повторить")
                    }
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(episodes.itemCount) { index ->
                        episodes[index]?.let { episode ->
                            EpisodeCard(
                                episode = episode,
                                onClick = { onEpisodeClick(episode.id) },
                            )
                        }
                    }
                    if (episodes.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    if (episodes.loadState.append is LoadState.Error) {
                        item {
                            Button(
                                onClick = { episodes.retry() },
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.rimColors.surface,
            ) {
                Text(
                    text = "S%02dE%02d".format(episode.episodeCode.season, episode.episodeCode.episodeNumber),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                )
            }
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(text = episode.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = episode.airDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.rimColors.textSecondary,
                )
            }
        }
    }
}
