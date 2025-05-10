package com.lhd.jetcinema

import android.app.Application
import com.lhd.jetcinema.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JetCinemaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDi()
    }

    private fun setupDi() {
        startKoin {
            androidLogger()
            androidContext(this@JetCinemaApplication)
            appModules
        }
    }

}