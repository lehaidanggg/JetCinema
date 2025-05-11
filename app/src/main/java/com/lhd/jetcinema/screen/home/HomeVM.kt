package com.lhd.jetcinema.screen.home

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
import kotlinx.coroutines.launch


class HomeVM(
    val movieRepository: MovieRepository
) : ViewModel() {
    private val TAG: String = this::class.simpleName.toString()

    private val _sliderUiState = MutableStateFlow<SliderUiState>(SliderUiState.Loading)
    val sliderUiState: StateFlow<SliderUiState> = _sliderUiState.asStateFlow()

    init {
        fetchSlider()
    }


    fun fetchSlider() {
        viewModelScope.launch {
            movieRepository
                .fetchSlider(countSlider = 7)
                .asResult()
                .collectLatest { sliderResult ->
                    when(sliderResult) {
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



    // ============================= STATE ===================================
    sealed class SliderUiState {
        data object Loading : SliderUiState()
        data class Error(val error: Throwable): SliderUiState()
        data class Success(val data: List<Movie>): SliderUiState()
    }
}