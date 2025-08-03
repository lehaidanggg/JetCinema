package com.lhd.jetcinema.presenter.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lhd.jetcinema.presenter.JetCinemaAppState
import com.lhd.jetcinema.presenter.main.tabs.home.HomeScreen
import com.lhd.jetcinema.presenter.theme.Colors

sealed class MainTab(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : MainTab("home", "Home", Icons.Default.Home)
    object Search : MainTab("search", "Search", Icons.Default.Search)
    object Favorite : MainTab("saved", "Saved", Icons.Default.Favorite)
    object Profile : MainTab("account", "Account", Icons.Default.Person)
}

@Composable
fun MainScreen(appState: JetCinemaAppState) {
    val navController = rememberNavController()

    Scaffold(
        containerColor = Colors.transparent,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            Column {
                HorizontalDivider(thickness = 1.dp, color = Colors.black)
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainTab.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MainTab.Home.route) { backStack ->
                HomeScreen(
                    navigateToDetail = { movie ->
                        appState.navController.currentBackStackEntry?.let { it ->
                            appState.navigateToDetail(movie = movie, from = it)
                        }
                    }
                )
            }
            composable(MainTab.Search.route) {
                HomeScreen(
                    navigateToDetail = { movie ->
                        appState.navController.currentBackStackEntry?.let { it ->
                            appState.navigateToDetail(movie = movie, from = it)
                        }
                    }
                )
            }
            composable(MainTab.Favorite.route) {
                HomeScreen(
                    navigateToDetail = { movie ->
                        appState.navController.currentBackStackEntry?.let { it ->
                            appState.navigateToDetail(movie = movie, from = it)
                        }
                    }
                )
            }
            composable(MainTab.Profile.route) {
                HomeScreen(
                    navigateToDetail = { movie ->
                        appState.navController.currentBackStackEntry?.let { it ->
                            appState.navigateToDetail(movie = movie, from = it)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val screens = listOf(
        MainTab.Home,
        MainTab.Search,
        MainTab.Favorite,
        MainTab.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Colors.primary
    ) {
        screens.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isSelected) Colors.orange else Colors.black
                        )
                    )
                },
                selected = isSelected,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Colors.transparent,
                    selectedIconColor = Colors.orange,
                    unselectedIconColor = Colors.black,
                    selectedTextColor = Colors.orange,
                    unselectedTextColor = Colors.black
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}