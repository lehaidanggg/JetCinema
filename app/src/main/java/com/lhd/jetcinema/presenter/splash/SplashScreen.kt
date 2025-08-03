package com.lhd.jetcinema.presenter.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.lhd.jetcinema.presenter.theme.Colors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewmodel: SplashVM = koinViewModel(),
    navigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewmodel.eventFlow.collectLatest { event ->
            when (event) {
                is SplashVM.UiEvent.UpdateGenre -> {
                    delay(2000)
                    navigateToHome.invoke()
                }
            }
        }
    }

    SplashContent()
}

@Composable
private fun SplashContent() {
    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Colors.primary)
                .padding(contentPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_app),
                contentDescription = "Logo splash",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(124.dp)
                    .align(Alignment.Center),
            )

            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                text = BuildConfig.VERSION_NAME,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSplash() {
    SplashContent()
}