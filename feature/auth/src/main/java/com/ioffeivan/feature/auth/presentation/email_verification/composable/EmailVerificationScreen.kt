package com.ioffeivan.feature.auth.presentation.email_verification.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.ioffeivan.feature.auth.R
import com.ioffeivan.feature.auth.presentation.email_verification.EmailVerificationEffect
import com.ioffeivan.feature.auth.presentation.email_verification.EmailVerificationEvent
import com.ioffeivan.feature.auth.presentation.email_verification.EmailVerificationState
import com.ioffeivan.feature.auth.presentation.email_verification.EmailVerificationViewModel
import com.ioffeivan.feature.auth.presentation.email_verification.OTP_LENGTH
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import com.ioffeivan.otp.compose.ui.basic.OtpField
import com.ioffeivan.otp.core.model.OtpLength

@Composable
internal fun EmailVerificationRoute(
    onNavigateToAccountCreated: () -> Unit,
    onShowSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier,
    viewModel: EmailVerificationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect,
        onEffect = {
            when (it) {
                EmailVerificationEffect.NavigateToAccountCreated ->
                    onNavigateToAccountCreated()

                is EmailVerificationEffect.ShowError ->
                    onShowSnackbar(it.message.asString(context), null)
            }
        },
    )

    EmailVerificationScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun EmailVerificationScreen(
    state: EmailVerificationState,
    onEvent: (EmailVerificationEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.otp) {
        if (state.otp.length == OTP_LENGTH) {
            onEvent(EmailVerificationEvent.OtpComplete)
            focusManager.clearFocus()
        }
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(top = 72.dp),
        ) {
            Text(
                text = stringResource(R.string.verify_your_email),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    ),
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnnotatedOtpSendMessage(
                email = state.email,
            )

            Spacer(modifier = Modifier.height(36.dp))

            OtpField(
                otp = state.otp,
                length = OtpLength(OTP_LENGTH),
                onOtpChange = {
                    onEvent(EmailVerificationEvent.OtpChange(it))
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .testTag("OtpField"),
            ) {
                OtpItem(
                    otpCell = it,
                    modifier =
                        Modifier
                            .weight(1f),
                )
            }
        }

        PrimaryButton(
            text = stringResource(R.string.resend_code_with_timer, state.timerState.displayTime),
            onClick =
                onDebounceClick {
                    onEvent(EmailVerificationEvent.ResendOtpClick)
                },
            enabled = !state.timerState.isActive,
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
    }

    if (state.isLoading) {
        LoadingScreen(
            modifier =
                Modifier
                    .fillMaxSize()
                    .testTag("loadingScreen"),
        )
    }
}

@Composable
private fun AnnotatedOtpSendMessage(
    email: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text =
            buildAnnotatedString {
                val message = stringResource(R.string.otp_sent_message, email)
                val emailStart = message.indexOf(email)
                val emailEnd = emailStart + email.length

                if (emailStart >= 0) {
                    append(message)
                    addStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold),
                        start = emailStart,
                        end = emailEnd,
                    )
                } else {
                    append(message)
                }
            },
        modifier = modifier,
        style =
            MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            ),
    )
}

@Preview
@Composable
private fun EmailVerificationScreenDefaultPreview() {
    PreviewContainer {
        EmailVerificationScreen(
            state =
                EmailVerificationState.initial(email = VALID_EMAIL),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun EmailVerificationScreenFilledPreview() {
    PreviewContainer {
        EmailVerificationScreen(
            state =
                EmailVerificationState.initial(email = VALID_EMAIL)
                    .copy(otp = "12345"),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun EmailVerificationScreenLoadingPreview() {
    PreviewContainer {
        EmailVerificationScreen(
            state =
                EmailVerificationState.initial(email = VALID_EMAIL)
                    .copy(isLoading = true),
            onEvent = {},
        )
    }
}
