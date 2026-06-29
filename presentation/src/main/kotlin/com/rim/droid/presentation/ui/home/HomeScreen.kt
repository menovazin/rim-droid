package com.rim.droid.presentation.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rim.droid.presentation.ui.characters.CharactersScreen
import com.rim.droid.presentation.ui.characters.CharactersViewModel
import com.rim.droid.presentation.ui.episodes.EpisodesScreen
import com.rim.droid.presentation.ui.episodes.EpisodesViewModel
import com.rim.droid.presentation.ui.locations.LocationsScreen
import com.rim.droid.presentation.ui.locations.LocationsViewModel
import kotlinx.coroutines.launch

enum class Section(val title: String) {
    CHARACTERS("Персонажи"),
    EPISODES("Эпизоды"),
    LOCATIONS("Локации"),
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "RIM Explorer",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                )
                Section.entries.forEach { section ->
                    NavigationDrawerItem(
                        label = { Text(section.title) },
                        selected = currentSection == section,
                        onClick = {
                            currentSection = section
                            scope.launch { drawerState.close() }
                        },
                    )
                }
                NavigationDrawerItem(
                    label = { Text("Выйти") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onLogout()
                    },
                )
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentSection.title) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    },
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
