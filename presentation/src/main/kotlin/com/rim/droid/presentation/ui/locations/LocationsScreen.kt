package com.rim.droid.presentation.ui.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rim.droid.domain.entity.Location
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.util.locationTypeIcon

@Composable
fun LocationsScreen(
    viewModel: LocationsViewModel,
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val locations = viewModel.locations.collectAsLazyPagingItems()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            locations.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            locations.loadState.refresh is LoadState.Error -> {
                val error = (locations.loadState.refresh as LoadState.Error).error
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Ошибка: ${error.localizedMessage}")
                    Button(onClick = { locations.retry() }) {
                        Text("Повторить")
                    }
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(locations.itemCount) { index ->
                        locations[index]?.let { location ->
                            LocationCard(
                                location = location,
                                onClick = { onLocationClick(location.id) },
                            )
                        }
                    }
                    if (locations.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    if (locations.loadState.append is LoadState.Error) {
                        item {
                            Button(
                                onClick = { locations.retry() },
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
private fun LocationCard(
    location: Location,
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
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.rimColors.surface),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = location.type.locationTypeIcon(),
                    contentDescription = location.type,
                    tint = MaterialTheme.rimColors.onSurface,
                )
            }
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(text = location.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${location.type} • ${location.dimension}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.rimColors.textSecondary,
                )
            }
        }
    }
}
