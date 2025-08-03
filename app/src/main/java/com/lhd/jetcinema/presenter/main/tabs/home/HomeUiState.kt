package com.lhd.jetcinema.presenter.main.tabs.home

import androidx.compose.runtime.Immutable
import com.lhd.jetcinema.domain.model.Genre
import com.lhd.jetcinema.domain.model.Movie
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isPurchased: Boolean = false,
    val categories: ImmutableList<Genre> = persistentListOf(),
    val populars: ImmutableList<Movie> = persistentListOf(),
    val recommends: ImmutableList<Movie> = persistentListOf()
)