package com.ioffeivan.feature.auth.presentation.composable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ioffeivan.core.designsystem.component.PrimaryTextField
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R

@Composable
internal fun UsernameTextField(
    username: String,
    onUsernameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: UiText? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    PrimaryTextField(
        value = username,
        onValueChange = onUsernameChange,
        label = label,
        placeholder = placeholder,
        leadingIcon = {
            PrimaryIcon(id = PrimaryIcons.Person)
        },
        supportingText = {
            if (isError) {
                errorMessage?.let {
                    Text(text = errorMessage.asString())
                }
            }
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun UsernameTextFieldEmptyPreview() {
    PreviewContainer {
        UsernameTextField(
            username = "",
            onUsernameChange = {},
            label = {
                Text(stringResource(R.string.username_label))
            },
            placeholder = {
                Text(stringResource(R.string.username_placeholder))
            },
        )
    }
}

@Preview
@Composable
private fun UsernameTextFieldFilledPreview() {
    PreviewContainer {
        UsernameTextField(
            username = "username123",
            onUsernameChange = {},
            label = {
                Text(stringResource(R.string.username_label))
            },
            placeholder = {
                Text(stringResource(R.string.username_placeholder))
            },
        )
    }
}

@Preview
@Composable
private fun UsernameTextFieldErrorPreview() {
    PreviewContainer {
        UsernameTextField(
            username = "short",
            onUsernameChange = {},
            label = {
                Text(stringResource(R.string.username_label))
            },
            placeholder = {
                Text(stringResource(R.string.username_placeholder))
            },
            isError = true,
            errorMessage = UiText.StringResource(R.string.error_username_invalid_chars),
        )
    }
}
