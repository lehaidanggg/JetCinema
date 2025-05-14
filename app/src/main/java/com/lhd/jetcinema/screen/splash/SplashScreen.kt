package com.lhd.jetcinema.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lhd.jetcinema.BuildConfig
import com.lhd.jetcinema.R
import com.lhd.jetcinema.screen.rememberMainAppState
import com.lhd.jetcinema.screen.theme.Colors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewmodel: SplashVM = koinViewModel(),
    navigateToOnboarding: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewmodel.eventFlow.collectLatest { event ->
            when (event) {
                is SplashVM.UiEvent.UpdateGenre -> {
                    delay(2000)
                    navigateToOnboarding.invoke()
                }
            }
        }
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Colors.primary)
                .padding(contentPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_splash),
                contentDescription = "Logo splash",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.BottomCenter),
                text = BuildConfig.VERSION_NAME,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Colors.white
                ),
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navigateToOnboarding = {})
}