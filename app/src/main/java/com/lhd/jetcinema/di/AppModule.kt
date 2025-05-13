package com.lhd.jetcinema.di

import com.lhd.jetcinema.data.repositoryImpl.MovieRepositoryImpl
import com.lhd.jetcinema.domain.repository.MovieRepository
import com.lhd.jetcinema.screen.detail_movie.DetailVM
import com.lhd.jetcinema.screen.home.HomeVM
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

interface NamedDI {
    companion object {
        const val DEFAULT_DISPATCHER = "defaultDispatcher"
        const val IO_DISPATCHER = "ioDispatcher"
        const val MAIN_DISPATCHER = "mainDispatcher"
        const val MAIN_IMMEDIATE_DISPATCHER = "mainImmediateDispatcher"
    }
}

val appModules = module {
    single(named(NamedDI.DEFAULT_DISPATCHER)) { Dispatchers.Default }
    single(named(NamedDI.IO_DISPATCHER)) { Dispatchers.IO }
    single(named(NamedDI.MAIN_DISPATCHER)) { Dispatchers.Main }
    single(named(NamedDI.MAIN_IMMEDIATE_DISPATCHER)) { Dispatchers.Main.immediate }
    // REPOSITORY
    single<MovieRepository> {
        MovieRepositoryImpl(
            dispatcher = get(named(NamedDI.IO_DISPATCHER)),
            movieApiService = get()
        )
    }

    // VIEWMODEL
    viewModel<HomeVM> { HomeVM(get()) }
    viewModel<DetailVM> { DetailVM() }
}