package com.lhd.jetcinema.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.lhd.jetcinema.screen.common.dialog.OfflineDialog
import com.lhd.jetcinema.screen.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun MainNavGraph(
    appState: JetCinemaAppState = rememberMainAppState()
) {
    if (appState.isOnline) {
        val navController = rememberNavController()
        val start = HomeScreenDestination
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root,
            startRoute = start,

        )
    } else {
        OfflineDialog { appState.refreshOnline() }
    }
}