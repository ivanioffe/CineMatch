package com.ioffeivan.feature.auth.presentation.composable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.ioffeivan.core.designsystem.component.PrimaryTextField
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.presentation.utils.PasswordValidator

@Composable
internal fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    onShowPasswordToggle: () -> Unit,
    modifier: Modifier = Modifier,
    showPassword: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: UiText? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    PrimaryTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = label,
        placeholder = placeholder,
        leadingIcon = {
            PrimaryIcon(id = PrimaryIcons.Lock)
        },
        trailingIcon = {
            PasswordVisibilityToggleIcon(
                showPassword = showPassword,
                onTogglePasswordVisibility = onShowPasswordToggle,
            )
        },
        supportingText = {
            if (isError) {
                errorMessage?.let {
                    Text(text = errorMessage.asString())
                }
            }
        },
        isError = isError,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        singleLine = true,
        modifier = modifier,
    )
}

@Composable
fun PasswordVisibilityToggleIcon(
    showPassword: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val icon =
        if (showPassword) {
            PrimaryIcons.Visibility
        } else {
            PrimaryIcons.VisibilityOff
        }

    IconButton(
        onClick = onTogglePasswordVisibility,
        modifier = modifier,
    ) {
        PrimaryIcon(id = icon)
    }
}

@Preview
@Composable
private fun PasswordTextFieldEmptyPreview() {
    PreviewContainer {
        PasswordTextField(
            password = "",
            onPasswordChange = {},
            onShowPasswordToggle = {},
            showPassword = false,
            label = {
                Text(stringResource(R.string.password_label))
            },
            placeholder = {
                Text(stringResource(R.string.password_placeholder))
            },
        )
    }
}

@Preview
@Composable
private fun PasswordTextFieldRevealedFilledPreview() {
    PreviewContainer {
        PasswordTextField(
            password = "MySecurePassword123",
            onPasswordChange = {},
            onShowPasswordToggle = {},
            showPassword = true,
            label = {
                Text(stringResource(R.string.password_label))
            },
            placeholder = {
                Text(stringResource(R.string.password_placeholder))
            },
        )
    }
}

@Preview
@Composable
private fun PasswordTextFieldMaskedFilledPreview() {
    PreviewContainer {
        PasswordTextField(
            password = "MySecurePassword123",
            onPasswordChange = {},
            onShowPasswordToggle = {},
            showPassword = false,
            label = {
                Text(stringResource(R.string.password_label))
            },
            placeholder = {
                Text(stringResource(R.string.password_placeholder))
            },
        )
    }
}

@Preview
@Composable
private fun PasswordTextFieldRevealedErrorPreview() {
    PreviewContainer {
        PasswordTextField(
            password = "123",
            onPasswordChange = {},
            onShowPasswordToggle = {},
            showPassword = true,
            label = {
                Text(stringResource(R.string.password_label))
            },
            placeholder = {
                Text(stringResource(R.string.password_placeholder))
            },
            isError = true,
            errorMessage =
                UiText.StringResource(
                    R.string.error_password_invalid_length,
                    args =
                        arrayOf(
                            PasswordValidator.MIN_LENGTH,
                            PasswordValidator.MAX_LENGTH,
                        ),
                ),
        )
    }
}

@Preview
@Composable
private fun PasswordTextFieldMaskedErrorPreview() {
    PreviewContainer {
        PasswordTextField(
            password = "123",
            onPasswordChange = {},
            onShowPasswordToggle = {},
            showPassword = false,
            label = {
                Text(stringResource(R.string.password_label))
            },
            placeholder = {
                Text(stringResource(R.string.password_placeholder))
            },
            isError = true,
            errorMessage =
                UiText.StringResource(
                    R.string.error_password_invalid_length,
                    args =
                        arrayOf(
                            PasswordValidator.MIN_LENGTH,
                            PasswordValidator.MAX_LENGTH,
                        ),
                ),
        )
    }
}
