package com.lhd.template.compose.screen.splash

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lhd.template.compose.R
import com.lhd.template.compose.screen.theme.gradientEnd
import com.lhd.template.compose.screen.theme.gradientStart
import com.lhd.template.compose.screen.theme.white

@Composable
fun SplashScreen() {
    BackgroundSplash {
//        Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
//                .padding(contentPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_logo_splash),
                contentDescription = "Mô tả ảnh",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(172.dp)
                    .padding(16.dp)
                    .align(Alignment.Center),
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
            ) {
                CircularProgressIndicator(
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    "This action can contain ads",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = white
                    )
                )
            }
        }
//        }
    }
}

@Composable
fun BackgroundSplash(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val brushGradient = Brush.verticalGradient(
        colors = listOf(gradientStart, gradientEnd)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = brushGradient)
    ) {
        content()
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}