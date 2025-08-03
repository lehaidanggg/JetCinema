package com.lhd.jetcinema.presenter

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lhd.jetcinema.presenter.common.dialog.OfflineDialog
import com.lhd.jetcinema.presenter.detail_movie.DetailMovieScreen
import com.lhd.jetcinema.presenter.main.tabs.home.HomeScreen
import com.lhd.jetcinema.presenter.main.MainScreen
import com.lhd.jetcinema.presenter.onboarding.OnboardingScreen
import com.lhd.jetcinema.presenter.splash.SplashScreen

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object Onboarding : Screens("onboarding")
    object Home : Screens("home")

    object Main : Screens("main")

    object Detail : Screens("detail/{$ARG_MOVIE}") {
        fun createRoute(movieJson: String): String {
            return "detail/$movieJson"
        }
    }

    companion object {
        const val ARG_MOVIE = "movieDetail"
    }
}

@Composable
fun MainNavGraph(
    appState: JetCinemaAppState = rememberMainAppState()
) {
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screens.Splash.route,
//            popExitTransition = { scaleOut(targetScale = 0.9f) },
//            popEnterTransition = { EnterTransition.None }
        ) {
            composable(Screens.Splash.route) { backStackEntry ->
                SplashScreen(
                    navigateToHome = {
                        appState.navigateToMain(backStackEntry)
                    }
                )
            }

            composable(Screens.Onboarding.route) { backStackEntry ->
                OnboardingScreen(
                    navigateToHome = {
                        appState.navigateToMain(backStackEntry)
                    }
                )
            }

            composable(Screens.Main.route) { backStackEntry ->
                MainScreen(appState = appState)
            }

            composable(Screens.Home.route) { backStackEntry ->
                HomeScreen(
                    navigateToDetail = { movie ->
                        appState.navigateToDetail(movie, backStackEntry)
                    }
                )
            }

            composable(
                route = Screens.Detail.route,
                arguments = listOf(navArgument(Screens.ARG_MOVIE) { type = NavType.StringType })
            ) { backStackEntry ->
//                val gson = com.google.gson.Gson().create()
//                val movieJson = backStackEntry.arguments?.getString(Screens.ARG_MOVIE)
//                val movie = gson.fromJson(
//                    movieJson?.let { Uri.decode(it) },
//                    Movie::class.java
//                )
                DetailMovieScreen(
                    navigateBack = {
                        appState.navigateBack()
                    }
                )
            }
        }
    } else {
        OfflineDialog { appState.refreshOnline() }
    }
}