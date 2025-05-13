package com.lhd.jetcinema.data.remote.api

import com.lhd.jetcinema.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    //https://api.themoviedb.org/3/trending/movie/day?language=en-US
    @GET("3/trending/movie/day?language=en-US")
    suspend fun fetchTrending() : MovieResponseDto


    //https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1
    @GET("3/movie/{category}")
    suspend fun fetchByCategory(
        @Path("category") category: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ) : MovieResponseDto
}