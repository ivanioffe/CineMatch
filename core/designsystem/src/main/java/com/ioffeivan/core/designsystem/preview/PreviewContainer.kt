package com.ioffeivan.core.designsystem.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.ioffeivan.core.designsystem.theme.CineMatchTheme

/**
 * A wrapper [Composable] used exclusively for Compose Previews.
 *
 * @param content The [Composable] content to be previewed.
 */
@Composable
fun PreviewContainer(
    content: @Composable () -> Unit,
) {
    CineMatchTheme {
        Surface(content = content)
    }
}
