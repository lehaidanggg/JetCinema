package com.lhd.jetcinema.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lhd.jetcinema.R
import com.lhd.jetcinema.screen.theme.Colors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        navigateToHome.invoke()
    }

    Scaffold { contentPadding ->
        BackgroundSplash(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_logo_splash),
                contentDescription = "Logo splash",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(172.dp)
                    .padding(16.dp)
                    .align(Alignment.Center),
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                CircularProgressIndicator(
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "This action can contain ads",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Colors.white
                    )
                )
            }
        }
    }
}

@Composable
fun BackgroundSplash(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val brushGradient = Brush.verticalGradient(
        colors = listOf(Colors.primary, Colors.grayScale60)
    )
    Box(
        modifier = modifier.background(brush = brushGradient),
        content = content
    )
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navigateToHome = {})
}