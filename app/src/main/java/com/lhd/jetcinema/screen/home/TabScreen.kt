package com.lhd.jetcinema.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TabScreen(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object HistoryTab : TabScreen("history", Icons.Default.Refresh, "History")
    object ScanTab : TabScreen("scan", Icons.Default.AccountCircle, "Scan")
    object SettingTab : TabScreen("setting", Icons.Default.Settings, "Setting")
}