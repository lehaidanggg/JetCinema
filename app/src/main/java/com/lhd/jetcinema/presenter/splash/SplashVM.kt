package com.lhd.jetcinema.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lhd.jetcinema.domain.model.Genre
import com.lhd.jetcinema.domain.repository.MovieRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashVM(
    val movieRepository: MovieRepository
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow


    init {
        fetchGenres()
    }

    private fun fetchGenres() {
        viewModelScope.launch {
            movieRepository
                .fetchGenre()
                .collectLatest { genres ->
                    _eventFlow.emit(
                        UiEvent.UpdateGenre(genres.toImmutableList())
                    )
                }
        }
    }

    sealed class UiEvent {
        data class UpdateGenre(val genres: ImmutableList<Genre>) : UiEvent()
    }
}