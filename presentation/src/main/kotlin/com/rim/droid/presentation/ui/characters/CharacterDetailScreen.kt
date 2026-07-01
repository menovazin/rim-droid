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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.rim.droid.R
import com.rim.droid.domain.entity.Character
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
    character: Character,
    onBack: () -> Unit,
) {
    val rimColors = MaterialTheme.rimColors

    Scaffold(
        containerColor = rimColors.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = character.name,
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.action_back))
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
                .verticalScroll(rememberScrollState()),
        ) {
            CharacterImage(characterId = character.id, characterName = character.name)

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(character.statusColor()),
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "${character.status} • ${character.species}",
                    style = MaterialTheme.typography.titleMedium,
                    color = rimColors.textPrimary,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                DetailInfoRow(label = stringResource(R.string.detail_species), value = character.species)
                if (character.type.isNotBlank()) {
                    DetailInfoRow(label = stringResource(R.string.detail_type), value = character.type)
                }
                DetailInfoRow(
                    label = stringResource(R.string.detail_gender),
                    value = "${character.gender.genderSymbol()}  ${character.gender}",
                )
                DetailInfoRow(label = stringResource(R.string.detail_origin), value = character.origin)
                DetailInfoRow(label = stringResource(R.string.detail_location), value = character.location)

                if (character.episodeIds.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    DetailSectionTitle(title = stringResource(R.string.section_episodes_count, character.episodeIds.size))
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        character.episodeIds.forEach { id ->
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
