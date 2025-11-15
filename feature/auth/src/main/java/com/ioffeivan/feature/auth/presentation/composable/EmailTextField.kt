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
import com.ioffeivan.core.designsystem.theme.CineMatchTheme
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.R

@Composable
internal fun EmailTextField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: UiText? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    PrimaryTextField(
        value = email,
        onValueChange = onEmailChange,
        label = label,
        placeholder = placeholder,
        leadingIcon = {
            PrimaryIcon(id = PrimaryIcons.Mail)
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

@Preview(name = "Empty State")
@Composable
private fun EmailTextFieldEmptyPreview() {
    CineMatchTheme {
        EmailTextField(
            email = "",
            onEmailChange = {},
            label = {
                Text(stringResource(R.string.email_label))
            },
            placeholder = {
                Text(stringResource(R.string.email_placeholder))
            },
        )
    }
}

@Preview(name = "Filled State")
@Composable
private fun EmailTextFieldFilledPreview() {
    CineMatchTheme {
        EmailTextField(
            email = "john.doe@email.com",
            onEmailChange = {},
            label = {
                Text(stringResource(R.string.email_label))
            },
            placeholder = {
                Text(stringResource(R.string.email_placeholder))
            },
        )
    }
}

@Preview(name = "Error State")
@Composable
private fun EmailTextFieldErrorPreview() {
    CineMatchTheme {
        EmailTextField(
            email = "invalid@",
            onEmailChange = {},
            label = {
                Text(stringResource(R.string.email_label))
            },
            placeholder = {
                Text(stringResource(R.string.email_placeholder))
            },
            isError = true,
            errorMessage = UiText.StringResource(R.string.error_email_invalid),
        )
    }
}
