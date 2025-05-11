package com.lhd.jetcinema.router

import com.lhd.jetcinema.domain.model.TypeScan

/**
 * List of screens for [com.lhd.jetcinema.screen.JetCinemaAppState]
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object ScanFile : Screen("scanFile/$ARG_TYPE_SCAN_FILE") {
        fun createRoute(typeScan: TypeScan) = "scanFile/${typeScan.value}"
    }

    object Player : Screen("player/{$ARG_EPISODE_URI}") {
        fun createRoute(episodeUri: String) = "player/$episodeUri"
    }

    object PodcastDetails : Screen("podcast/{$ARG_PODCAST_URI}") {
        fun createRoute(podcastUri: String) = "podcast/$podcastUri"
    }

    companion object {
        val ARG_TYPE_SCAN_FILE = "typeScan"
        val ARG_PODCAST_URI = "podcastUri"
        val ARG_EPISODE_URI = "episodeUri"
    }
}