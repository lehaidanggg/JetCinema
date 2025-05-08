package com.lhd.template.compose.screen

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.scaleOut
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lhd.template.compose.screen.home.HomeScreen
import com.lhd.template.compose.screen.splash.SplashScreen

@Composable
fun MainApp(
    appState: MainAppState = rememberMainAppState()
) {
    if (appState.isOnline) {
        NavHost(
            navController = appState.navController,
            startDestination = Screen.Splash.route,
            popExitTransition = { scaleOut(targetScale = 0.9f) },
            popEnterTransition = { EnterTransition.None }
        ) {
            composable(Screen.Splash.route) {
                SplashScreen()
            }

            composable(Screen.Home.route) { backStackEntry ->
                HomeScreen(
//                    windowSizeClass = adaptiveInfo.windowSizeClass,
//                    navigateToPlayer = { episode ->
//                        appState.navigateToPlayer(episode.uri, backStackEntry)
//                    }
                )
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

@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "stringResource(R.string.connection_error_title)") },
        text = { Text(text = "stringResource(R.string.connection_error_message)") },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("stringResource(R.string.retry_label)")
            }
        }
    )
}