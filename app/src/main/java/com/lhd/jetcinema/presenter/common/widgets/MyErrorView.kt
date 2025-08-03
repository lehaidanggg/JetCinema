package com.lhd.jetcinema.presenter.common.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun MyErrorView(
    modifier: Modifier = Modifier,
    error: String
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = error,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview
@Composable
fun ErrorViewPreview() {
    MyErrorView(
        error = "This is demo error!"
    )
}
