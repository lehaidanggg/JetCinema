package com.lhd.jetcinema.presenter.detail_movie

import com.lhd.jetcinema.domain.model.Movie

data class DetailMovieState(
    val isLoading: Boolean = false,
    val throwable: Throwable? = null,
    val movie: Movie? = null
)

sealed class DetailMovieEvent() {
    object Idle : DetailMovieEvent()
    object OnBackPress : DetailMovieEvent()


}