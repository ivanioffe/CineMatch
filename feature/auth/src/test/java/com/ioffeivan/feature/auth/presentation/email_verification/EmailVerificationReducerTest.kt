package com.ioffeivan.feature.auth.presentation.email_verification

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.feature.auth.presentation.email_verification.utils.ERROR_INVALID_OTP
import com.ioffeivan.feature.auth.presentation.email_verification.utils.INVALID_OTP_MESSAGE
import com.ioffeivan.feature.auth.presentation.email_verification.utils.emailVerificationLoadingState
import com.ioffeivan.feature.auth.presentation.utils.DataError
import com.ioffeivan.feature.auth.presentation.utils.VALID_EMAIL
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EmailVerificationReducerTest {
    private lateinit var reducer: EmailVerificationReducer
    private lateinit var state: EmailVerificationState

    @BeforeEach
    fun setUp() {
        reducer = EmailVerificationReducer()
        state = EmailVerificationState.initial(VALID_EMAIL)
    }

    @Test
    fun emailVerificationError_shouldSetIsLoadingFalseInStateAndEmitShowErrorEffect() {
        val loadingState = emailVerificationLoadingState
        val event = EmailVerificationEvent.EmailVerificationError(INVALID_OTP_MESSAGE)
        val expected =
            createReducerResult(
                state = loadingState.copy(isLoading = false),
                effect = EmailVerificationEffect.ShowError(ERROR_INVALID_OTP),
            )

        val actual = reducer.reduce(loadingState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun emailVerificationSuccess_shouldSetIsLoadingFalseInStateAndEmitNavigateToAccountCreatedEffect() {
        val loadingState = emailVerificationLoadingState
        val event = EmailVerificationEvent.EmailVerificationSuccess
        val expected =
            createReducerResult(
                state = loadingState.copy(isLoading = false),
                effect = EmailVerificationEffect.NavigateToAccountCreated,
            )

        val actual = reducer.reduce(loadingState, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun otpChange_shouldUpdateOtpInState() {
        val otp = "123"
        val event = EmailVerificationEvent.OtpChange(otp)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        otp = otp,
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun otpComplete_shouldSetIsLoadingTrueInState() {
        val event = EmailVerificationEvent.OtpComplete
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        isLoading = true,
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun resendOtpClick_shouldReturnsSameState() {
        val event = EmailVerificationEvent.ResendOtpClick
        val expected =
            createReducerResult(
                state = state,
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun resendOtpError_shouldReturnsSameState() {
        val event = EmailVerificationEvent.ResendOtpError("error")
        val expected =
            createReducerResult(
                state = state,
                effect = EmailVerificationEffect.ShowError(DataError.somethingWentWrong),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun timerStateChange_shouldUpdateTimerStateInState() {
        val timerState = EmailVerificationState.TimerState.Running(45)
        val event = EmailVerificationEvent.TimerStateChange(timerState)
        val expected =
            createReducerResult(
                state =
                    state.copy(
                        timerState = timerState,
                    ),
            )

        val actual = reducer.reduce(state, event)

        assertThat(actual).isEqualTo(expected)
    }

    private fun createReducerResult(
        state: EmailVerificationState,
        effect: EmailVerificationEffect? = null,
    ): ReducerResult<EmailVerificationState, EmailVerificationEffect> {
        return ReducerResult(state = state, effect = effect)
    }
}
