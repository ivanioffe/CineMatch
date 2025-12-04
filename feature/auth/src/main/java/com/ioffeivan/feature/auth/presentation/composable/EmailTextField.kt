package com.ioffeivan.feature.auth.presentation.composable

import androidx.compose.foundation.text.KeyboardActions
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
import com.ioffeivan.feature.auth.presentation.utils.ValidationErrors

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
    keyboardActions: KeyboardActions = KeyboardActions.Default,
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
        keyboardActions = keyboardActions,
        singleLine = true,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun EmailTextFieldEmptyPreview() {
    PreviewContainer {
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

@Preview
@Composable
private fun EmailTextFieldFilledPreview() {
    PreviewContainer {
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

@Preview
@Composable
private fun EmailTextFieldErrorPreview() {
    PreviewContainer {
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
            errorMessage = ValidationErrors.emailInvalid,
        )
    }
}
