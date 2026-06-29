package com.rim.droid.presentation.ui.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.rim.droid.domain.entity.Character
import com.rim.droid.presentation.util.AvatarUrlUtils

@Composable
fun CharactersScreen(
    viewModel: CharactersViewModel,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            characters.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            characters.loadState.refresh is LoadState.Error -> {
                val error = (characters.loadState.refresh as LoadState.Error).error
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Ошибка: ${error.localizedMessage}")
                    Button(onClick = { characters.retry() }) {
                        Text("Повторить")
                    }
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(160.dp),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(characters.itemCount) { index ->
                        characters[index]?.let { character ->
                            CharacterCard(
                                character = character,
                                onClick = { onCharacterClick(character.id) },
                            )
                        }
                    }
                    if (characters.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    if (characters.loadState.append is LoadState.Error) {
                        item {
                            Button(
                                onClick = { characters.retry() },
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
private fun CharacterCard(
    character: Character,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Column {
            AsyncImage(
                model = AvatarUrlUtils.avatarUrlFromId(character.id),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(200.dp),
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}
