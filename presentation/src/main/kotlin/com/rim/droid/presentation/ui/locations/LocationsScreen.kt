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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rim.droid.R
import com.rim.droid.domain.entity.Location
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.util.locationTypeIcon

@Composable
fun LocationsScreen(
    viewModel: LocationsViewModel,
    onLocationClick: (Location) -> Unit,
    modifier: Modifier = Modifier,
) {
    val locations = viewModel.locations.collectAsLazyPagingItems()
    val rimColors = MaterialTheme.rimColors

    Box(modifier = modifier.fillMaxSize()) {
        when {
            locations.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = rimColors.primary,
                )
            }
            locations.loadState.refresh is LoadState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.state_error_generic),
                        color = rimColors.textSecondary,
                    )
                    Button(
                        onClick = { locations.retry() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = rimColors.primary,
                            contentColor = rimColors.onPrimary,
                        ),
                    ) {
                        Text(stringResource(R.string.action_retry))
                    }
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(
                        count = locations.itemCount,
                        key = { index -> locations[index]?.id ?: "placeholder-$index" },
                    ) { index ->
                        locations[index]?.let { location ->
                            LocationCard(
                                location = location,
                                onClick = { onLocationClick(location) },
                            )
                        }
                    }
                    if (locations.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(color = rimColors.primary)
                            }
                        }
                    }
                    if (locations.loadState.append is LoadState.Error) {
                        item {
                            Button(
                                onClick = { locations.retry() },
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = rimColors.primary,
                                    contentColor = rimColors.onPrimary,
                                ),
                            ) {
                                Text(stringResource(R.string.action_retry_loading))
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
    modifier: Modifier = Modifier,
) {
    val rimColors = MaterialTheme.rimColors

    Box(
        modifier = modifier
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
                    .clip(CircleShape)
                    .background(rimColors.secondary.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = location.type.locationTypeIcon(),
                    contentDescription = location.type,
                    tint = rimColors.secondary,
                )
            }
            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                Text(
                    text = location.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.W700,
                    color = rimColors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${location.type.ifEmpty { "Unknown" }} • ${location.dimension.ifEmpty { "Unknown" }}",
                    style = MaterialTheme.typography.bodySmall,
                    color = rimColors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Box(modifier = Modifier.padding(start = 8.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = rimColors.textSecondary,
                )
            }
        }
    }
}
