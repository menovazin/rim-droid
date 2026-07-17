package com.rim.droid.presentation.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.rim.droid.R
import com.rim.droid.domain.entity.Character
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.ui.common.ZoomableNetworkImage
import com.rim.droid.presentation.util.statusColor

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel,
    onCharacterClick: (Character) -> Unit,
    modifier: Modifier = Modifier,
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()
    val rimColors = MaterialTheme.rimColors

    Box(modifier = modifier.fillMaxSize()) {
        when {
            characters.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = rimColors.primary,
                )
            }
            characters.loadState.refresh is LoadState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.state_error_generic),
                        color = rimColors.textSecondary,
                    )
                    Button(
                        onClick = { characters.retry() },
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
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(180.dp),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        count = characters.itemCount,
                        key = { index -> characters[index]?.id ?: "placeholder-$index" },
                    ) { index ->
                        characters[index]?.let { character ->
                            CharacterCard(
                                character = character,
                                onClick = { onCharacterClick(character) },
                            )
                        }
                    }
                    if (characters.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(color = rimColors.primary)
                            }
                        }
                    }
                    if (characters.loadState.append is LoadState.Error) {
                        item {
                            Button(
                                onClick = { characters.retry() },
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
private fun CharacterCard(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val rimColors = MaterialTheme.rimColors
    // Raise whole card above LazyGrid siblings while zooming (image-only zIndex is not enough).
    var zoomActive by remember { mutableStateOf(false) }

    // Background uses shape for rounded chrome but does not clip children, so zoomed
    // images can overflow the card. Rest-state image rounding is owned by ZoomableNetworkImage.
    Column(
        modifier = modifier
            .zIndex(if (zoomActive) 1f else 0f)
            .fillMaxWidth()
            .aspectRatio(0.72f)
            .background(rimColors.surface, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
    ) {
        ZoomableNetworkImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier.fillMaxWidth().weight(1f),
            clipShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            onClick = onClick,
            onZoomActiveChange = { zoomActive = it },
        )
        Column(
            modifier = Modifier.padding(start = 10.dp, top = 8.dp, end = 10.dp, bottom = 10.dp),
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.W700,
                color = rimColors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.size(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(character.statusColor()),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${character.status} • ${character.species}",
                    style = MaterialTheme.typography.bodySmall,
                    color = rimColors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
