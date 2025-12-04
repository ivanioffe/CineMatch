package com.ioffeivan.feature.auth.presentation.email_verification

import com.ioffeivan.core.presentation.Reducer
import com.ioffeivan.core.presentation.ReducerResult
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.auth.presentation.email_verification.utils.EmailVerificationDataError
import java.util.Locale

internal const val OTP_LENGTH = 5

internal class EmailVerificationReducer :
    Reducer<EmailVerificationState, EmailVerificationEvent, EmailVerificationEffect> {
    override fun reduce(
        previousState: EmailVerificationState,
        event: EmailVerificationEvent,
    ): ReducerResult<EmailVerificationState, EmailVerificationEffect> {
        return when (event) {
            is EmailVerificationEvent.EmailVerificationError -> {
                val error = EmailVerificationDataError.getError(event.message)

                ReducerResult(
                    state =
                        previousState.copy(
                            otp = "",
                            isLoading = false,
                        ),
                    effect = EmailVerificationEffect.ShowError(error),
                )
            }

            EmailVerificationEvent.EmailVerificationSuccess -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = false,
                        ),
                    effect = EmailVerificationEffect.NavigateToAccountCreated,
                )
            }

            is EmailVerificationEvent.OtpChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            otp = event.otp,
                        ),
                )
            }

            is EmailVerificationEvent.OtpComplete -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            isLoading = true,
                        ),
                )
            }

            EmailVerificationEvent.ResendOtpClick -> {
                ReducerResult(
                    state = previousState,
                )
            }

            is EmailVerificationEvent.ResendOtpError -> {
                val error = EmailVerificationDataError.getError(event.message)

                ReducerResult(
                    state = previousState,
                    effect = EmailVerificationEffect.ShowError(error),
                )
            }

            is EmailVerificationEvent.TimerStateChange -> {
                ReducerResult(
                    state =
                        previousState.copy(
                            timerState = event.timerState,
                        ),
                )
            }
        }
    }
}

internal data class EmailVerificationState(
    val email: String,
    val otp: String,
    val timerState: TimerState,
    val isLoading: Boolean,
) : Reducer.UiState {
    sealed class TimerState {
        abstract val displayTime: String

        val isActive
            get() = this is Running

        data class Running(
            val remainingSeconds: Int,
        ) : TimerState() {
            override val displayTime: String =
                String.format(
                    locale = Locale.ROOT,
                    format = "%02d:%02d",
                    remainingSeconds / 60,
                    remainingSeconds % 60,
                )
        }

        data object Finished : TimerState() {
            override val displayTime: String = ""
        }
    }

    companion object {
        fun initial(email: String): EmailVerificationState {
            return EmailVerificationState(
                email = email,
                otp = "",
                timerState = TimerState.Running(RESEND_OTP_SECONDS),
                isLoading = false,
            )
        }
    }
}

internal sealed interface EmailVerificationEvent : Reducer.UiEvent {
    data class OtpChange(val otp: String) : EmailVerificationEvent

    data object OtpComplete : EmailVerificationEvent

    data class TimerStateChange(val timerState: EmailVerificationState.TimerState) :
        EmailVerificationEvent

    data object ResendOtpClick : EmailVerificationEvent

    data object EmailVerificationSuccess : EmailVerificationEvent

    data class EmailVerificationError(val message: String?) : EmailVerificationEvent

    data class ResendOtpError(val message: String?) : EmailVerificationEvent
}

internal sealed interface EmailVerificationEffect : Reducer.UiEffect {
    data object NavigateToAccountCreated : EmailVerificationEffect

    data class ShowError(val message: UiText) : EmailVerificationEffect
}
