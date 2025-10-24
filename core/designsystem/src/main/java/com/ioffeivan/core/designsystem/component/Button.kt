package com.ioffeivan.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ioffeivan.core.designsystem.theme.CineMatchTheme

/**
 * Primary button component for Design System.
 *
 * Displays a filled button with content slot.
 * Uses Material3 [Button] with primary color from [MaterialTheme.colorScheme].
 *
 * @param onClick Callback invoked when the button is clicked
 * @param modifier Modifier for layout customization
 * @param enabled Controls whether the button is clickable.
 * @param contentPadding Padding inside the button.
 * @param content Composable content to be displayed inside the button.
 */
@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val shape = RoundedCornerShape(4.dp)

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        contentPadding = contentPadding,
        modifier =
            modifier
                .height(60.dp)
                .dropShadow(
                    shape = shape,
                    shadow =
                        Shadow(
                            radius = 16.dp,
                            spread = 4.dp,
                            color = MaterialTheme.colorScheme.primary,
                            alpha = 0.25f,
                        ),
                ),
        content = content,
    )
}

/**
 * Primary button component with text content for Design System.
 *
 * Overload of [PrimaryButton] that displays a text.
 *
 * @param text The text to display inside the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier Modifier for layout customization.
 * @param enabled Controls whether the button is clickable.
 * @param contentPadding Padding inside the button.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    PrimaryButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
    ) {
        Text(
            text = text,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style =
                MaterialTheme.typography.labelLarge
                    .copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W800,
                    ),
        )
    }
}

/**
 * Primary outlined button component for Design System.
 *
 * Displays an outlined button with content slot.
 * Uses Material3 [OutlinedButton] with primary color from [MaterialTheme.colorScheme].
 *
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier Modifier for layout customization.
 * @param enabled Controls whether the button is clickable.
 * @param contentPadding Padding inside the button.
 * @param content Composable content to be displayed inside the button.
 */
@Composable
fun PrimaryOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    val shape = RoundedCornerShape(4.dp)
    val border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)

    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        border = border,
        contentPadding = contentPadding,
        modifier =
            modifier
                .height(60.dp),
        content = content,
    )
}

/**
 * Primary outlined button component with text content for Design System.
 *
 * Overload of [PrimaryOutlinedButton] that displays a text.
 *
 * @param text The text to display inside the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier Modifier for layout customization.
 * @param enabled Controls whether the button is clickable.
 * @param contentPadding Padding inside the button.
 */
@Composable
fun PrimaryOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
) {
    PrimaryOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
    ) {
        Text(
            text = text,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style =
                MaterialTheme.typography.labelLarge
                    .copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W800,
                    ),
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    CineMatchTheme {
        PrimaryButton(
            text = "Button",
            onClick = {},
            modifier =
                Modifier
                    .padding(16.dp),
        )
    }
}

@Preview
@Composable
private fun PrimaryOutlinedButtonPreview() {
    CineMatchTheme {
        PrimaryOutlinedButton(
            text = "Button",
            onClick = {},
        )
    }
}
