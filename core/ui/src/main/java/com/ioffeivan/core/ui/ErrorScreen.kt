package com.ioffeivan.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String = stringResource(R.string.loading_error),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = errorMessage,
            style =
                MaterialTheme.typography.bodyLarge
                    .copy(
                        textAlign = TextAlign.Center,
                    ),
        )

        Button(
            onClick = onDebounceClick(onClick = onRetryClick),
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}
