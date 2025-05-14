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
import com.lhd.jetcinema.data.remote.dto.GenreDto
import com.lhd.jetcinema.domain.model.Genre


@Composable
fun rememberMainAppState(
    context: Context = LocalContext.current
) = remember(context) {
    JetCinemaAppState(context)
}

class JetCinemaAppState(
    private val context: Context
) {
    var isOnline by mutableStateOf(checkIfOnline())
        private set

    val genres = mutableListOf<Genre>()

    fun refreshOnline() {
        isOnline = checkIfOnline()
    }

    fun setGenres(genres: MutableList<Genre>) {
        this.genres.clear()
        this.genres.addAll(genres)
    }

    @Suppress("DEPRECATION")
    private fun checkIfOnline(): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)

        val capabilities = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
