package com.lhd.jetcinema.presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.util.lifecycleIsResumed

@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(context) {
    JetCinemaAppState(navController, context)
}

class JetCinemaAppState(
    val navController: NavHostController,
    val context: Context
) {
    var isOnline by mutableStateOf(checkIfOnline())
        private set

    @Suppress("DEPRECATION")
    private fun checkIfOnline(): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)

        val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    fun refreshOnline() {
        isOnline = checkIfOnline()
    }

    // NAVIGATION
    fun navigateToOnBoarding(from: NavBackStackEntry) {
        if (!from.lifecycleIsResumed()) return
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()

        navController.navigate(
            route = Screens.Onboarding.route,
            navOptions = options
        )
    }

    fun navigateToHome(from: NavBackStackEntry) {
        if (!from.lifecycleIsResumed()) return
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()

        navController.navigate(
            route = Screens.Home.route,
            navOptions = options
        )
    }
    fun navigateToMain(from: NavBackStackEntry) {
        if (!from.lifecycleIsResumed()) return
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()

        navController.navigate(
            route = Screens.Main.route,
            navOptions = options
        )
    }

    fun navigateToDetail(movie: Movie, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {

            val gson: Gson = GsonBuilder().create()
            val userJson = Uri.encode(gson.toJson(movie))
            navController.navigate(Screens.Detail.createRoute(userJson))
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}
