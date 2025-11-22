package com.ioffeivan.feature.auth.presentation.login.composable

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
import com.ioffeivan.feature.auth.presentation.login.LoginEffect
import com.ioffeivan.feature.auth.presentation.login.LoginEvent
import com.ioffeivan.feature.auth.presentation.login.LoginState
import com.ioffeivan.feature.auth.presentation.login.LoginViewModel
import com.ioffeivan.feature.auth.presentation.login.utils.loginInvalidState
import com.ioffeivan.feature.auth.presentation.login.utils.loginLoadingState
import com.ioffeivan.feature.auth.presentation.login.utils.loginValidState
import com.ioffeivan.feature.auth.presentation.utils.Colors
import kotlinx.coroutines.flow.filterIsInstance
import com.ioffeivan.core.ui.R as coreR
import com.ioffeivan.feature.auth.R as authR

@Composable
internal fun LoginRoute(
    onNavigateToSignUp: () -> Unit,
    onNavigateToMain: () -> Unit,
    onShowSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect.filterIsInstance<LoginEffect.Ui>(),
        onEffect = {
            when (it) {
                LoginEffect.Ui.NavigateToSignUp -> onNavigateToSignUp()
                LoginEffect.Ui.NavigateToMain -> onNavigateToMain()
                is LoginEffect.Ui.ShowError -> onShowSnackbar(it.message.asString(context), null)
            }
        },
    )

    LoginScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    val imeAction = if (state.isFilled()) ImeAction.Done else ImeAction.Next
    val onLogin: () -> Unit = {
        onEvent(LoginEvent.LoginClick)
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
            text = stringResource(coreR.string.login),
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
                    onEvent(LoginEvent.EmailChange(it))
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
                        onDone = { onLogin() },
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("emailTextField"),
            )

            PasswordTextField(
                password = state.password.value,
                onPasswordChange = {
                    onEvent(LoginEvent.PasswordChange(it))
                },
                onShowPasswordToggle = {
                    onEvent(LoginEvent.PasswordVisibilityToggle)
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
                        imeAction = ImeAction.Done,
                    ),
                keyboardActions =
                    KeyboardActions(
                        onDone = { onLogin() },
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("passwordTextField"),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        PrimaryButton(
            text = stringResource(authR.string.log_in),
            onClick =
                onDebounceClick(onClick = onLogin),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag("loginButton"),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(authR.string.not_have_account),
                style = MaterialTheme.typography.bodyMedium,
            )

            Text(
                text = stringResource(coreR.string.signup),
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = Colors.Lilac,
                        fontWeight = FontWeight.Bold,
                    ),
                modifier =
                    Modifier
                        .clickable(
                            onClick = {
                                onEvent(LoginEvent.SignUpClick)
                            },
                        )
                        .testTag("signUpText"),
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
private fun LoginScreenDefaultPreview() {
    PreviewContainer {
        LoginScreen(
            state = LoginState.initial(),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun LoginScreenValidPreview() {
    PreviewContainer {
        LoginScreen(
            state = loginValidState,
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun LoginScreenLoadingPreview() {
    PreviewContainer {
        LoginScreen(
            state = loginLoadingState,
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun LoginScreenInvalidPreview() {
    PreviewContainer {
        LoginScreen(
            state = loginInvalidState,
            onEvent = {},
        )
    }
}
