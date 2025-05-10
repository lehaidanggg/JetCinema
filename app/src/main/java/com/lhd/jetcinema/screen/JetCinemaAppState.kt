package com.lhd.jetcinema.screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lhd.jetcinema.data.model.TypeScan
import com.lhd.jetcinema.router.Screen
import com.lhd.jetcinema.util.lifecycleIsResumed


@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    JetCinemaAppState(navController, context)
}

class JetCinemaAppState(
    val navController: NavHostController,
    private val context: Context
) {
    var isOnline by mutableStateOf(checkIfOnline())
        private set

    val navigationFunc: NavigationFunc
        get() = NavigationFunc(navController)


    fun refreshOnline() {
        isOnline = checkIfOnline()
    }


    @Suppress("DEPRECATION")
    private fun checkIfOnline(): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)

        val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}

class NavigationFunc(
    val navController: NavHostController
) {

    fun navigateToHome(from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate(Screen.Home.route)
        }
    }

    fun navigateToScanFile(typeScan: TypeScan, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(Screen.ScanFile.createRoute(typeScan))
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }

}
