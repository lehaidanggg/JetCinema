package com.lhd.jetcinema.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.domain.repository.MovieRepository
import com.lhd.jetcinema.util.Result
import com.lhd.jetcinema.util.asResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class HomeVM(
    val movieRepository: MovieRepository
) : ViewModel() {
    private val TAG: String = this::class.simpleName.toString()

    private val _sliderUiState = MutableStateFlow<SliderUiState>(SliderUiState.Loading)
    val sliderUiState: StateFlow<SliderUiState> = _sliderUiState.asStateFlow()

    private val _nowPlayingUiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val nowPlayingUiState: StateFlow<MovieUiState> = _nowPlayingUiState.asStateFlow()

    private val _popularUiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val popularUiState: StateFlow<MovieUiState> = _popularUiState.asStateFlow()


    private var _page: Int = 1

    init {
        fetchSlider()
        fetchMovieNowPlaying()
        fetchMoviePopular()
    }


    fun fetchSlider() {
        viewModelScope.launch {
            movieRepository
                .fetchSlider(countSlider = 7)
                .asResult()
                .collectLatest { sliderResult ->
                    when (sliderResult) {
                        Result.Loading -> {
                            _sliderUiState.tryEmit(SliderUiState.Loading)
                        }

                        is Result.Error -> {
                            val exception = sliderResult.exception
                            _sliderUiState.tryEmit(SliderUiState.Error(exception))
                        }

                        is Result.Success -> {
                            val data = sliderResult.data
                            _sliderUiState.tryEmit(SliderUiState.Success(data))
                        }
                    }
                }
        }
    }

    fun fetchMovieNowPlaying() {
        viewModelScope.launch {
            movieRepository.fetchByCategory(
                category = "now_playing",
                page = _page
            )
                .asResult()
                .collectLatest { nowPlayingUiState ->
                    when (nowPlayingUiState) {
                        Result.Loading -> {
                            _nowPlayingUiState.tryEmit(MovieUiState.Loading)
                        }

                        is Result.Error -> {
                            val exception = nowPlayingUiState.exception
                            _nowPlayingUiState.tryEmit(MovieUiState.Error(exception))
                        }

                        is Result.Success -> {
                            val movies = nowPlayingUiState.data
                            _nowPlayingUiState.tryEmit(MovieUiState.Success(movies))
                        }
                    }
                }
        }
    }

    fun fetchMoviePopular() {
        viewModelScope.launch {
            movieRepository.fetchByCategory(
                category = "popular",
                page = _page
            )
                .asResult()
                .collectLatest { nowPlayingUiState ->
                    when (nowPlayingUiState) {
                        Result.Loading -> {
                            _popularUiState.tryEmit(MovieUiState.Loading)
                        }

                        is Result.Error -> {
                            val exception = nowPlayingUiState.exception
                            _popularUiState.tryEmit(MovieUiState.Error(exception))
                        }

                        is Result.Success -> {
                            val movies = nowPlayingUiState.data
                            _popularUiState.tryEmit(MovieUiState.Success(movies))
                        }
                    }
                }
        }
    }

    // ============================= STATE ===================================
    sealed class SliderUiState {
        data object Loading : SliderUiState()
        data class Error(val error: Throwable) : SliderUiState()
        data class Success(val data: List<Movie>) : SliderUiState()
    }

    sealed class MovieUiState {
        data object Loading : MovieUiState()
        data class Error(val error: Throwable) : MovieUiState()
        data class Success(val data: List<Movie>) : MovieUiState()
    }
}