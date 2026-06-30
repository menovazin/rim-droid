package com.rim.droid.presentation.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.ui.common.DetailChip
import com.rim.droid.presentation.ui.common.DetailInfoRow
import com.rim.droid.presentation.ui.common.DetailSectionTitle
import com.rim.droid.presentation.util.AvatarUrlUtils
import com.rim.droid.presentation.util.genderSymbol
import com.rim.droid.presentation.util.statusColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onBack: () -> Unit,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
) {
    val character by viewModel.character.collectAsStateWithLifecycle()
    val rimColors = MaterialTheme.rimColors

    Scaffold(
        containerColor = rimColors.background,
        topBar = {
            TopAppBar(
                title = { Text(character?.name ?: "Загрузка...") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = rimColors.background,
                    titleContentColor = rimColors.textPrimary,
                    navigationIconContentColor = rimColors.textPrimary,
                ),
            )
        },
    ) { paddingValues ->
        val ch = character
        if (ch == null) {
            Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = rimColors.primary)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
        ) {
            CharacterImage(characterId = ch.id, characterName = ch.name)

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(ch.statusColor()),
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "${ch.status} • ${ch.species}",
                    style = MaterialTheme.typography.titleMedium,
                    color = rimColors.textPrimary,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                DetailInfoRow(label = "Вид", value = ch.species)
                if (ch.type.isNotBlank()) {
                    DetailInfoRow(label = "Тип", value = ch.type)
                }
                DetailInfoRow(
                    label = "Пол",
                    value = "${ch.gender.genderSymbol()}  ${ch.gender}",
                )
                DetailInfoRow(label = "Происхождение", value = ch.origin)
                DetailInfoRow(label = "Локация", value = ch.location)

                if (ch.episodeIds.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailSectionTitle(title = "Эпизоды (${ch.episodeIds.size})")
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        ch.episodeIds.forEach { id ->
                            DetailChip(label = "E${id.toString().padStart(2, '0')}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterImage(characterId: Int, characterName: String) {
    val rimColors = MaterialTheme.rimColors
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .aspectRatio(1f)
            .fillMaxWidth(),
    ) {
        SubcomposeAsyncImage(
            model = AvatarUrlUtils.avatarUrlFromId(characterId),
            contentDescription = characterName,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize().background(rimColors.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = rimColors.primary)
                }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize().background(rimColors.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.BrokenImage,
                        contentDescription = null,
                        tint = rimColors.textSecondary,
                    )
                }
            },
        )
    }
}
