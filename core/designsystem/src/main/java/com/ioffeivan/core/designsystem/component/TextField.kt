package com.ioffeivan.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioffeivan.core.designsystem.theme.CineMatchTheme

/**
 * Primary text field component for Design System.
 *
 * A customizable [OutlinedTextField] with consistent styling.
 *
 * @param value The current text value of the text field.
 * @param onValueChange Callback invoked when the text value changes.
 * @param modifier Modifier for layout customization.
 * @param enabled Controls whether the text field is editable.
 * @param label Optional composable for displaying a label above the text field.
 * @param placeholder Optional composable for placeholder text when the field is empty.
 * @param leadingIcon Optional composable for an icon at the start of the text field.
 * @param trailingIcon Optional composable for an icon at the end of the text field.
 * @param supportingText Optional composable for helper or error text below the text field.
 * @param isError Indicates if the text field is in an error state.
 * @param visualTransformation Transformation for text display.
 * @param keyboardOptions Configuration for the keyboard.
 * @param keyboardActions Actions for keyboard events.
 * @param singleLine If true, restricts the text field to a single line.
 * @param maxLines Maximum number of lines (defaults to 1 if [singleLine] is true, else unlimited).
 * @param minLines Minimum number of lines to display.
 * @param interactionSource Optional [MutableInteractionSource] for handling interaction events.
 * @param colors Custom [TextFieldColors] for styling.
 */
@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        isError = isError,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(4.dp),
        colors = colors,
    )
}

@Preview
@Composable
private fun PrimaryTextFieldPreview() {
    CineMatchTheme {
        PrimaryTextField(
            value = "",
            onValueChange = {
            },
        )
    }
}
