package com.lhd.jetcinema.data.repositoryImpl

import com.lhd.jetcinema.data.remote.api.MovieApiService
import com.lhd.jetcinema.data.remote.dto.MovieResponseDto
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    val dispatcher: CoroutineDispatcher,
    val movieApiService: MovieApiService
) : MovieRepository {
    private val tag = this::class.simpleName.toString()


    override fun fetchSlider(countSlider: Int) = flow<List<Movie>> {
        val movies = movieApiService
            .fetchTrending()
            .movieDtos
            .map { it.toDomain() }
            .take(countSlider)
        emit(movies)
    }.flowOn(dispatcher)

    override fun fetchTrendingMovie() = flow<List<Movie>> {
        val movies = movieApiService
            .fetchTrending()
            .movieDtos
            .map { it.toDomain() }
        emit(movies)
    }.flowOn(dispatcher)

    override fun fetchByCategory(category: String, page: Int): Flow<List<Movie>> = flow<List<Movie>> {
        val movies = movieApiService
            .fetchByCategory(
                category = category,
                page = page
            )
            .movieDtos
            .map { it.toDomain() }
        emit(movies)
    }.flowOn(dispatcher)
}