package com.lhd.jetcinema.router

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lhd.jetcinema.screen.JetCinemaAppState
import com.lhd.jetcinema.screen.common.dialog.OfflineDialog
import com.lhd.jetcinema.screen.home.HomeScreen
import com.lhd.jetcinema.screen.onboarding.OnboardingScreen
import com.lhd.jetcinema.screen.rememberMainAppState
import com.lhd.jetcinema.screen.scan_file.ScanFileScreen
import com.lhd.jetcinema.screen.splash.SplashScreen

@Composable
fun MainNavGraph(
    appState: JetCinemaAppState = rememberMainAppState()
) {
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screen.Home.route,
            popExitTransition = { scaleOut(targetScale = 0.9f) },
            popEnterTransition = { EnterTransition.None }
        ) {
            composable(Screen.Splash.route) { backStackEntry ->
                SplashScreen(
                    navigateToOnboarding = {
                        appState.navigationAction.navigateOnBoarding(backStackEntry)
                    }
                )
            }
            composable(Screen.Onboarding.route) { backStackEntry ->
                OnboardingScreen(
                    navigateToHome = {
                        appState.navigationAction.navigateToHome(backStackEntry)
                    }
                )
            }

            composable(Screen.Home.route) { backStackEntry ->
                HomeScreen(
                    navigateScanFile = { typeScan ->
                        appState.navigationAction.navigateToScanFile(typeScan, backStackEntry)
                    }
                )
            }

            composable(route = Screen.ScanFile.route) { backStackEntry ->
                ScanFileScreen()
            }

            composable(Screen.Player.route) {
//                PlayerScreen(
//                    windowSizeClass = adaptiveInfo.windowSizeClass,
//                    displayFeatures = displayFeatures,
//                    onBackPress = appState::navigateBack
//                )
            }
        }
    } else {
        OfflineDialog { appState.refreshOnline() }
    }
}