package com.lhd.jetcinema.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lhd.jetcinema.presenter.theme.MainTheme
import com.lhd.jetcinema.util.SystemBarUtils

class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SystemBarUtils.setStatusBarIconTheme(this, false)
        setContent {
            MainTheme {
                MainNavGraph()
            }
        }
    }
}