package com.lhd.jetcinema.screen.common.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "stringResource(R.string.connection_error_title)") },
        text = { Text(text = "stringResource(R.string.connection_error_message)") },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("stringResource(R.string.retry_label)")
            }
        }
    )
}