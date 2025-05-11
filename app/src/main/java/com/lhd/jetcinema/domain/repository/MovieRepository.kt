package com.lhd.jetcinema.domain.repository

import com.lhd.jetcinema.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchTrendingMovie() : Flow<List<Movie>>
    fun fetchSlider(countSlider: Int = 5) : Flow<List<Movie>>
}