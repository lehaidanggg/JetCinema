package com.lhd.jetcinema.presenter.common.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun BuildError(
    modifier: Modifier = Modifier,
    error: Throwable?
) {
    Text(
        modifier = modifier,
        text = error?.message ?: "",
        style = MaterialTheme.typography.headlineMedium
    )
}