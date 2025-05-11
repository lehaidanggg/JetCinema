package com.lhd.jetcinema.data.remote.api

import com.lhd.jetcinema.data.remote.dto.ResultMovieDto
import retrofit2.http.GET

interface MovieApiService {

    //https://api.themoviedb.org/3/trending/movie/day?language=en-US
    @GET("3/trending/movie/day?language=en-US")
    suspend fun fetchTrending() : ResultMovieDto
}