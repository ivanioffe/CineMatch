package com.ioffeivan.feature.auth.presentation.sign_up.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ioffeivan.core.designsystem.component.PrimaryButton
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.ui.LoadingScreen
import com.ioffeivan.core.ui.ObserveEffectsWithLifecycle
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.core.ui.onDebounceClick
import com.ioffeivan.feature.auth.presentation.composable.EmailTextField
import com.ioffeivan.feature.auth.presentation.composable.PasswordTextField
import com.ioffeivan.feature.auth.presentation.composable.UsernameTextField
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpEffect
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpEvent
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpState
import com.ioffeivan.feature.auth.presentation.sign_up.SignUpViewModel
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpStateFilled
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpStateLoading
import com.ioffeivan.feature.auth.presentation.sign_up.utils.signUpStateValidationError
import com.ioffeivan.feature.auth.presentation.utils.Colors
import kotlinx.coroutines.flow.filterIsInstance
import com.ioffeivan.core.ui.R as coreR
import com.ioffeivan.feature.auth.R as authR

@Composable
internal fun SignUpRoute(
    onNavigateToLogin: () -> Unit,
    onNavigateToVerifyEmail: () -> Unit,
    onShowSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect.filterIsInstance<SignUpEffect.Ui>(),
        onEffect = {
            when (it) {
                SignUpEffect.Ui.NavigateToLogin -> onNavigateToLogin()
                SignUpEffect.Ui.NavigateToVerifyEmail -> onNavigateToVerifyEmail()
                is SignUpEffect.Ui.ShowError -> onShowSnackbar(it.message.asString(context), null)
            }
        },
    )

    SignUpScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun SignUpScreen(
    state: SignUpState,
    onEvent: (SignUpEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    val imeAction = if (state.isFilledState()) ImeAction.Done else ImeAction.Next
    val onDone: KeyboardActionScope.() -> Unit = {
        onEvent(SignUpEvent.SignUpClick)
        focusManager.clearFocus()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
    ) {
        Text(
            text = stringResource(coreR.string.signup),
            style =
                MaterialTheme.typography.titleMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            EmailTextField(
                email = state.email.value,
                onEmailChange = {
                    onEvent(SignUpEvent.EmailChange(it))
                },
                label = {
                    Text(
                        text = stringResource(authR.string.email_label),
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(authR.string.email_placeholder),
                    )
                },
                isError = state.email.isError,
                errorMessage = state.email.errorMessage,
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = imeAction,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = onDone,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("emailTextField"),
            )

            UsernameTextField(
                username = state.username.value,
                onUsernameChange = {
                    onEvent(SignUpEvent.UsernameChange(it))
                },
                label = {
                    Text(
                        text = stringResource(authR.string.username_label),
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(authR.string.username_placeholder),
                    )
                },
                isError = state.username.isError,
                errorMessage = state.username.errorMessage,
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = imeAction,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = onDone,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("usernameTextField"),
            )

            PasswordTextField(
                password = state.password.value,
                onPasswordChange = {
                    onEvent(SignUpEvent.PasswordChange(it))
                },
                onShowPasswordToggle = {
                    onEvent(SignUpEvent.PasswordVisibilityToggle(false))
                },
                showPassword = state.password.visibility,
                label = {
                    Text(
                        text = stringResource(authR.string.password_label),
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(authR.string.password_placeholder),
                    )
                },
                isError = state.password.isError,
                errorMessage = state.password.errorMessage,
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = imeAction,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = onDone,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("passwordTextField"),
            )

            PasswordTextField(
                password = state.confirmPassword.value,
                onPasswordChange = {
                    onEvent(SignUpEvent.ConfirmPasswordChange(it))
                },
                onShowPasswordToggle = {
                    onEvent(SignUpEvent.PasswordVisibilityToggle(true))
                },
                showPassword = state.confirmPassword.visibility,
                label = {
                    Text(
                        text = stringResource(authR.string.confirm_password_label),
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(authR.string.confirm_password_placeholder),
                    )
                },
                isError = state.confirmPassword.isError,
                errorMessage = state.confirmPassword.errorMessage,
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = onDone,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("confirmPasswordTextField"),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = stringResource(authR.string.create_account),
            onClick =
                onDebounceClick {
                    onEvent(SignUpEvent.SignUpClick)
                },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag("signUpButton"),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(authR.string.already_have_account),
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = stringResource(coreR.string.login),
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = Colors.Lilac,
                        fontWeight = FontWeight.Bold,
                    ),
                modifier =
                    Modifier
                        .clickable(
                            onClick = {
                                onEvent(SignUpEvent.LoginClick)
                            },
                        )
                        .testTag("loginText"),
            )
        }
    }

    if (state.isLoading) {
        LoadingScreen(
            modifier =
                Modifier
                    .fillMaxSize(),
        )
    }
}

@Preview
@Composable
private fun SignUpScreenDefaultPreview() {
    PreviewContainer {
        SignUpScreen(
            state = SignUpState.initial(),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun SignUpScreenFilledPreview() {
    PreviewContainer {
        SignUpScreen(
            state = signUpStateFilled,
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun SignUpScreenLoadingPreview() {
    PreviewContainer {
        SignUpScreen(
            state = signUpStateLoading,
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun SignUpScreenValidationErrorPreview() {
    PreviewContainer {
        SignUpScreen(
            state = signUpStateValidationError,
            onEvent = {},
        )
    }
}
