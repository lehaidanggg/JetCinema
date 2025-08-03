package com.lhd.jetcinema.presenter.main.tabs.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lhd.jetcinema.domain.repository.MovieRepository
import com.lhd.jetcinema.util.Constants
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeVM(
    val movieRepository: MovieRepository
) : ViewModel() {
    private val tag: String = this::class.simpleName.toString()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private var _page: Int = 1
    private val _pageSize = 12

    init { fetchHomePageContent() }


    fun fetchHomePageContent() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val flowFetchGenres = movieRepository.fetchGenre()
            val flowFetchPopulars = movieRepository.fetchByCategory(
                category = Constants.POPULAR,
                page = _page,
                count = _pageSize
            )
            val flowFetchRecommends = movieRepository.fetchByCategory(
                category = Constants.NOW_PLAYING,
                page = _page,
                count = _pageSize,
            )

            combine(
                flowFetchGenres,
                flowFetchPopulars,
                flowFetchRecommends
            ) { sliders, populars, recommends ->
                Triple(sliders, populars, recommends)
            }
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e
                        )
                    }
                }
                .collectLatest { tripleMovie ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            categories = tripleMovie.first.toPersistentList(),
                            populars = tripleMovie.second.toPersistentList(),
                            recommends = tripleMovie.third.toPersistentList(),
                        )
                    }
                }
        }
    }
}