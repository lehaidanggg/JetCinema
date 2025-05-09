package com.lhd.template.compose.router

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lhd.template.compose.screen.MainAppState
import com.lhd.template.compose.screen.common.dialog.OfflineDialog
import com.lhd.template.compose.screen.home.HomeScreen
import com.lhd.template.compose.screen.rememberMainAppState
import com.lhd.template.compose.screen.scan_file.ScanFileScreen
import com.lhd.template.compose.screen.splash.SplashScreen

@Composable
fun MainNavGraph(
    appState: MainAppState = rememberMainAppState()
) {
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screen.Splash.route,
            popExitTransition = { scaleOut(targetScale = 0.9f) },
            popEnterTransition = { EnterTransition.None }
        ) {
            composable(Screen.Splash.route) { backStackEntry ->
                SplashScreen {
                    appState.navigationFunc.navigateToHome(backStackEntry)
                }
            }

            composable(Screen.Home.route) { backStackEntry ->
                HomeScreen(
                    navigateScanFile = { typeScan ->
                        appState.navigationFunc.navigateToScanFile(typeScan, backStackEntry)
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