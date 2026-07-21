package com.rim.droid.presentation.ui.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rim.droid.R
import com.rim.droid.domain.entity.Location
import com.rim.droid.presentation.theme.RimDesignTokens
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.theme.rimDetailHeroGradient
import com.rim.droid.presentation.ui.common.CharacterAvatarCircle
import com.rim.droid.presentation.util.locationTypeIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    location: Location,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val rimColors = MaterialTheme.rimColors
    var scale by remember { mutableFloatStateOf(1f) }
    val locType = location.type.ifEmpty { "Unknown" }
    val dim = location.dimension.ifEmpty { "Unknown" }

    Scaffold(
        modifier = modifier,
        containerColor = rimColors.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = location.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = rimColors.textPrimary,
                        fontWeight = FontWeight.W700,
                        textAlign = TextAlign.Center,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back),
                        )
                    }
                },
                actions = {
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTransformGestures { _, _, zoom, _ ->
                        scale = (scale * zoom).coerceIn(0.5f, 3f)
                    }
                }
                .graphicsLayer(scaleX = scale, scaleY = scale),
        ) {
            LocationHeaderCard(
                location = location,
                locType = locType,
                dim = dim,
                rimColors = rimColors,
            )
            Spacer(modifier = Modifier.height(24.dp))
            LocationResidentsSection(location = location, rimColors = rimColors)
        }
    }
}

@Composable
private fun LocationHeaderCard(
    location: Location,
    locType: String,
    dim: String,
    rimColors: RimDesignTokens,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                rimDetailHeroGradient(
                    accent = rimColors.secondary,
                    surface = rimColors.surface,
                    background = rimColors.background,
                ),
            )
            .padding(20.dp),
    ) {
        Column {
            Icon(
                imageVector = location.type.locationTypeIcon(),
                contentDescription = location.type,
                tint = rimColors.secondary,
                modifier = Modifier.size(40.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineSmall,
                color = rimColors.textPrimary,
                fontWeight = FontWeight.W700,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Type badge (filled)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(rimColors.secondary)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = locType,
                        style = MaterialTheme.typography.labelMedium,
                        color = rimColors.onSecondary,
                        fontWeight = FontWeight.W600,
                    )
                }
                // Dimension badge (outlined)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = rimColors.secondary.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp),
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = dim,
                        style = MaterialTheme.typography.labelMedium,
                        color = rimColors.secondary,
                        fontWeight = FontWeight.W600,
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationResidentsSection(
    location: Location,
    rimColors: RimDesignTokens,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.section_residents_count, location.residentIds.size),
            style = MaterialTheme.typography.titleMedium,
            color = rimColors.primary,
            fontWeight = FontWeight.W700,
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (location.residentIds.isEmpty()) {
            Text(
                text = stringResource(R.string.no_residents),
                style = MaterialTheme.typography.bodyMedium,
                color = rimColors.textSecondary,
            )
        } else {
            LazyRow(
                modifier = Modifier.height(72.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = location.residentIds,
                    key = { it },
                ) { residentId ->
                    CharacterAvatarCircle(characterId = residentId, name = "#$residentId")
                }
            }
        }
    }
}
