package com.lhd.jetcinema.di

import com.lhd.jetcinema.BuildConfig
import com.lhd.jetcinema.data.remote.api.MovieApiService
import com.lhd.jetcinema.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        // Interceptor thêm header Authorization
        Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer ${BuildConfig.MOVIE_APIKEY}")
                .build()
            chain.proceed(request)
        }
    }
    single {
        // Logging interceptor (chỉ nên dùng debug)
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>()) // auth interceptor
            .addInterceptor(get<HttpLoggingInterceptor>()) // log
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<MovieApiService> { get<Retrofit>().create(MovieApiService::class.java) }
}