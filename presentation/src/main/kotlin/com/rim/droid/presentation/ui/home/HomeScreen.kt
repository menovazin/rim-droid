package com.rim.droid.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rim.droid.presentation.theme.RimBaseColors
import com.rim.droid.presentation.theme.RimTheme
import com.rim.droid.presentation.theme.rimColors
import com.rim.droid.presentation.ui.characters.CharactersScreen
import com.rim.droid.presentation.ui.characters.CharactersViewModel
import com.rim.droid.presentation.ui.episodes.EpisodesScreen
import com.rim.droid.presentation.ui.episodes.EpisodesViewModel
import com.rim.droid.presentation.ui.locations.LocationsScreen
import com.rim.droid.presentation.ui.locations.LocationsViewModel
import kotlinx.coroutines.launch

enum class Section(val title: String, val icon: ImageVector) {
    CHARACTERS("Персонажи", Icons.Outlined.PeopleAlt),
    EPISODES("Эпизоды", Icons.Outlined.Movie),
    LOCATIONS("Локации", Icons.Outlined.Public),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCharacterClick: (Int) -> Unit,
    onEpisodeClick: (Int) -> Unit,
    onLocationClick: (Int) -> Unit,
    onLogout: () -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentSection by remember { mutableStateOf(Section.CHARACTERS) }
    val rimColors = MaterialTheme.rimColors

    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Black.copy(alpha = 0.4f),
        drawerContent = {
            RimDrawerContent(
                currentSection = currentSection,
                onSectionClick = { section ->
                    currentSection = section
                    scope.launch { drawerState.close() }
                },
                onLogout = {
                    scope.launch { drawerState.close() }
                    onLogout()
                },
            )
        },
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = currentSection.title,
                            color = rimColors.textPrimary,
                            textAlign = TextAlign.Center,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Меню",
                            )
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
            when (currentSection) {
                Section.CHARACTERS -> {
                    val viewModel = hiltViewModel<CharactersViewModel>()
                    CharactersScreen(
                        viewModel = viewModel,
                        onCharacterClick = onCharacterClick,
                        modifier = Modifier.padding(paddingValues),
                    )
                }
                Section.EPISODES -> {
                    val viewModel = hiltViewModel<EpisodesViewModel>()
                    EpisodesScreen(
                        viewModel = viewModel,
                        onEpisodeClick = onEpisodeClick,
                        modifier = Modifier.padding(paddingValues),
                    )
                }
                Section.LOCATIONS -> {
                    val viewModel = hiltViewModel<LocationsViewModel>()
                    LocationsScreen(
                        viewModel = viewModel,
                        onLocationClick = onLocationClick,
                        modifier = Modifier.padding(paddingValues),
                    )
                }
            }
        }
    }
}

@Composable
private fun RimDrawerContent(
    currentSection: Section,
    onSectionClick: (Section) -> Unit,
    onLogout: () -> Unit,
) {
    val rimColors = MaterialTheme.rimColors

    ModalDrawerSheet(
        drawerContainerColor = RimBaseColors.black,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
        ) {
            // Brand header
            Row(
                modifier = Modifier.padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Science,
                    contentDescription = null,
                    tint = rimColors.primary,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Rick & Morty",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = rimColors.textPrimary,
                        fontWeight = FontWeight.W700,
                    ),
                )
            }

            HorizontalDivider(
                color = rimColors.textSecondary.copy(alpha = 0.15f),
                modifier = Modifier.height(16.dp)
            )

            // Section items
            Section.entries.forEach { section ->
                RimDrawerItem(
                    icon = section.icon,
                    label = section.title,
                    selected = currentSection == section,
                    onClick = { onSectionClick(section) },
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider(
                color = rimColors.textSecondary.copy(alpha = 0.15f),
                modifier = Modifier.height(16.dp)
            )

            // Logout
            RimDrawerItem(
                icon = Icons.AutoMirrored.Outlined.Logout,
                label = "Выйти",
                selected = false,
                onClick = onLogout,
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun RimDrawerItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val rimColors = MaterialTheme.rimColors
    val backgroundColor = if (selected) {
        rimColors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }
    val contentColor = if (selected) rimColors.primary else rimColors.textSecondary
    val textColor = if (selected) rimColors.textPrimary else rimColors.textSecondary
    val fontWeight = if (selected) FontWeight.W700 else FontWeight.W500

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(22.dp),
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall.copy(
                color = textColor,
                fontWeight = fontWeight,
            ),
        )
    }
}

@Suppress("UnusedPrivateMember")
@Preview(name = "Drawer Content", showBackground = true)
@Composable
private fun RimDrawerContentPreview() {
    RimTheme {
        RimDrawerContent(
            currentSection = Section.CHARACTERS,
            onSectionClick = {},
            onLogout = {},
        )
    }
}
