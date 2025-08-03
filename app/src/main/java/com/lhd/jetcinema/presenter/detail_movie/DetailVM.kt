package com.lhd.jetcinema.presenter.detail_movie

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.presenter.Screens
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailVM(
    val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailMovieState>(DetailMovieState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<DetailMovieEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init { getMovie() }

    fun sendEvent(uiEvent: DetailMovieEvent) {
        when(uiEvent) {
            DetailMovieEvent.Idle -> {}
            DetailMovieEvent.OnBackPress -> onBack()
        }
    }

    private fun getMovie() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val movieJson = savedStateHandle.get<String>(Screens.ARG_MOVIE)
            val gson = GsonBuilder().create()
            val movie = gson.fromJson(
                movieJson?.let { Uri.decode(it) },
                Movie::class.java
            )

            _uiState.update {
                it.copy(isLoading = false, movie = movie)
            }
        }
    }

    private fun onBack() {
        viewModelScope.launch {
            _uiEvent.send(DetailMovieEvent.OnBackPress)
        }
    }
}