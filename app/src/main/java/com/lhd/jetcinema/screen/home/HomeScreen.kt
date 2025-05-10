package com.lhd.jetcinema.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lhd.jetcinema.data.model.TypeScan
import com.lhd.jetcinema.screen.home.tabs.HistoryTab
import com.lhd.jetcinema.screen.home.tabs.ScanTab
import com.lhd.jetcinema.screen.home.tabs.SettingTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateScanFile: (TypeScan) -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { contentPadding ->

        NavHost(
            navController = navController,
            startDestination = TabScreen.ScanTab.route,
            modifier = Modifier.padding(contentPadding)
        ) {
            composable(TabScreen.HistoryTab.route) { HistoryTab() }
            composable(TabScreen.ScanTab.route) {
                ScanTab(
                    navigateScanFile = navigateScanFile
                )
            }
            composable(TabScreen.SettingTab.route) { SettingTab() }
        }
    }
}


@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        TabScreen.HistoryTab,
        TabScreen.ScanTab,
        TabScreen.SettingTab
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

// =================================== PREVIEW ====================================
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateScanFile = {}
    )
}