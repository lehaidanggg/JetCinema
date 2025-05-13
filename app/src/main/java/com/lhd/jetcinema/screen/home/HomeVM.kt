package com.lhd.jetcinema.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.domain.repository.MovieRepository
import com.lhd.jetcinema.util.Constants
import com.lhd.jetcinema.util.Result
import com.lhd.jetcinema.util.asResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeVM(
    val movieRepository: MovieRepository
) : ViewModel() {
    private val TAG: String = this::class.simpleName.toString()

    private val _sliderUiState = MutableStateFlow<SliderUiState>(SliderUiState.Loading)
    val sliderUiState: StateFlow<SliderUiState> = _sliderUiState.asStateFlow()

    private val _popularUiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val popularUiState: StateFlow<MovieUiState> = _popularUiState.asStateFlow()

    private val _topRatedUiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val topRatedUiState: StateFlow<MovieUiState> = _topRatedUiState.asStateFlow()


    private var _page: Int = 1

    init {
        fetchSlider()
        fetchMoviePopular()
        fetchMovieTopRated()
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

    fun fetchMoviePopular() {
        viewModelScope.launch {
            movieRepository.fetchByCategory(
                category = Constants.POPULAR,
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

    fun fetchMovieTopRated() {
        viewModelScope.launch {
            movieRepository.fetchByCategory(
                category = Constants.TOP_RATED,
                page = _page
            )
                .asResult()
                .collectLatest { nowPlayingUiState ->
                    when (nowPlayingUiState) {
                        Result.Loading -> {
                            _topRatedUiState.tryEmit(MovieUiState.Loading)
                        }

                        is Result.Error -> {
                            val exception = nowPlayingUiState.exception
                            _topRatedUiState.tryEmit(MovieUiState.Error(exception))
                        }

                        is Result.Success -> {
                            val movies = nowPlayingUiState.data
                            _topRatedUiState.tryEmit(MovieUiState.Success(movies))
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