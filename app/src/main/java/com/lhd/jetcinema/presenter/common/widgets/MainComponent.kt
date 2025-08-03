package com.lhd.jetcinema.presenter.common.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lhd.jetcinema.presenter.theme.Colors

@Composable
fun MainComponent(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(
            width = 2.dp,
            color = Colors.black
        ),
        color = Colors.primary,
        contentColor = Colors.primary,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = alignment
        ) {
            content()
        }
    }
}