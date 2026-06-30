package com.rim.droid.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rim.droid.domain.entity.Character
import com.rim.droid.domain.entity.Episode
import com.rim.droid.domain.entity.Location
import com.rim.droid.presentation.ui.characters.CharacterDetailScreen
import com.rim.droid.presentation.ui.episodes.EpisodeDetailScreen
import com.rim.droid.presentation.ui.home.HomeScreen
import com.rim.droid.presentation.ui.locations.LocationDetailScreen
import com.rim.droid.presentation.ui.login.LoginScreen
import com.rim.droid.presentation.ui.login.LoginViewModel
import kotlin.reflect.typeOf

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
                onCharacterClick = { character -> navController.navigate(CharacterDetailRoute(character)) },
                onEpisodeClick = { episode -> navController.navigate(EpisodeDetailRoute(episode)) },
                onLocationClick = { location -> navController.navigate(LocationDetailRoute(location)) },
                onLogout = {
                    onLogout()
                    navController.navigate(LoginRoute) {
                        popUpTo<HomeRoute> { inclusive = true }
                    }
                },
            )
        }

        composable<CharacterDetailRoute>(
            typeMap = mapOf(typeOf<Character>() to CharacterNavType),
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<CharacterDetailRoute>()
            CharacterDetailScreen(character = route.character, onBack = { navController.popBackStack() })
        }

        composable<EpisodeDetailRoute>(
            typeMap = mapOf(typeOf<Episode>() to EpisodeNavType),
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<EpisodeDetailRoute>()
            EpisodeDetailScreen(episode = route.episode, onBack = { navController.popBackStack() })
        }

        composable<LocationDetailRoute>(
            typeMap = mapOf(typeOf<Location>() to LocationNavType),
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<LocationDetailRoute>()
            LocationDetailScreen(location = route.location, onBack = { navController.popBackStack() })
        }
    }
}
