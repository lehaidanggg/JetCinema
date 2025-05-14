package com.lhd.jetcinema.domain.repository

import com.lhd.jetcinema.domain.model.Genre
import com.lhd.jetcinema.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun fetchSlider(countSlider: Int = 5) : Flow<List<Movie>>
    fun fetchTrendingMovie() : Flow<List<Movie>>
    fun fetchByCategory(category: String, page: Int) : Flow<List<Movie>>
    fun fetchGenre(language: String = "en-US") : Flow<List<Genre>>
}