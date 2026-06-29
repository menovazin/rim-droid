package com.rim.droid.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rim.droid.presentation.ui.characters.CharacterDetailScreen
import com.rim.droid.presentation.ui.episodes.EpisodeDetailScreen
import com.rim.droid.presentation.ui.home.HomeScreen
import com.rim.droid.presentation.ui.locations.LocationDetailScreen
import com.rim.droid.presentation.ui.login.LoginScreen
import com.rim.droid.presentation.ui.login.LoginViewModel

@Composable
fun RimNavHost(
    hasToken: Boolean,
    onLogout: () -> Unit,
) {
    val navController = rememberNavController()
    val startDestination = if (hasToken) HomeRoute else LoginRoute

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
    ) {
        composable<LoginRoute> {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                },
            )
        }

        composable<HomeRoute> {
            HomeScreen(
                onCharacterClick = { id -> navController.navigate(CharacterDetailRoute(id)) },
                onEpisodeClick = { id -> navController.navigate(EpisodeDetailRoute(id)) },
                onLocationClick = { id -> navController.navigate(LocationDetailRoute(id)) },
                onLogout = {
                    onLogout()
                    navController.navigate(LoginRoute) {
                        popUpTo<HomeRoute> { inclusive = true }
                    }
                },
            )
        }

        composable<CharacterDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<CharacterDetailRoute>()
            CharacterDetailScreen(characterId = route.characterId, onBack = { navController.popBackStack() })
        }

        composable<EpisodeDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<EpisodeDetailRoute>()
            EpisodeDetailScreen(episodeId = route.episodeId, onBack = { navController.popBackStack() })
        }

        composable<LocationDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<LocationDetailRoute>()
            LocationDetailScreen(locationId = route.locationId, onBack = { navController.popBackStack() })
        }
    }
}
