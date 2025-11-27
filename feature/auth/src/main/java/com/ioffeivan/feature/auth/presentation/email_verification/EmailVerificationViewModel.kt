package com.ioffeivan.feature.auth.presentation.email_verification

import androidx.lifecycle.viewModelScope
import com.ioffeivan.core.common.result.onError
import com.ioffeivan.core.common.result.onException
import com.ioffeivan.core.common.result.onSuccess
import com.ioffeivan.core.presentation.BaseViewModel
import com.ioffeivan.feature.auth.domain.model.EmailVerificationRequest
import com.ioffeivan.feature.auth.domain.model.ResendOtpRequest
import com.ioffeivan.feature.auth.domain.usecase.CountdownTimerUseCase
import com.ioffeivan.feature.auth.domain.usecase.ResendOtpUseCase
import com.ioffeivan.feature.auth.domain.usecase.VerifyWithOtpUseCase
import dagger.Lazy
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal const val RESEND_OTP_SECONDS = 180

@HiltViewModel(assistedFactory = EmailVerificationViewModel.Factory::class)
internal class EmailVerificationViewModel @AssistedInject constructor(
    private val verifyWithOtpUseCase: VerifyWithOtpUseCase,
    private val resendOtpUseCase: Lazy<ResendOtpUseCase>,
    private val countdownTimerUseCase: CountdownTimerUseCase,
    @Assisted email: String,
) : BaseViewModel<EmailVerificationState, EmailVerificationEvent, EmailVerificationEffect>(
        initialState = EmailVerificationState.initial(email),
        reducer = EmailVerificationReducer(),
    ) {
    init {
        startResendTimer()
    }

    override fun onEvent(event: EmailVerificationEvent) {
        sendEvent(event)

        when (event) {
            is EmailVerificationEvent.OtpComplete -> {
                verifyEmail(state.value.toEmailVerificationRequest())
            }

            is EmailVerificationEvent.ResendOtpClick -> {
                startResendTimer()
                resendOtp(state.value.toResendOtpRequest())
            }

            else -> {}
        }
    }

    private fun verifyEmail(emailVerificationRequest: EmailVerificationRequest) {
        viewModelScope.launch {
            verifyWithOtpUseCase(emailVerificationRequest)
                .onSuccess {
                    sendEvent(EmailVerificationEvent.EmailVerificationSuccess)
                }.onError {
                    sendEvent(EmailVerificationEvent.EmailVerificationError(it))
                }.onException {
                    sendEvent(EmailVerificationEvent.EmailVerificationError(it.message))
                }
        }
    }

    private fun resendOtp(resendOtpRequest: ResendOtpRequest) {
        viewModelScope.launch {
            resendOtpUseCase.get().invoke(resendOtpRequest)
                .onException {
                    sendEvent(EmailVerificationEvent.EmailVerificationError(it.message))
                }
        }
    }

    private fun startResendTimer() {
        countdownTimerUseCase(RESEND_OTP_SECONDS)
            .onEach {
                sendEvent(
                    if (it == 0) {
                        EmailVerificationEvent.TimerStateChange(
                            EmailVerificationState.TimerState.Finished,
                        )
                    } else {
                        EmailVerificationEvent.TimerStateChange(
                            EmailVerificationState.TimerState.Running(
                                remainingSeconds = it,
                            ),
                        )
                    },
                )
            }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            email: String,
        ): EmailVerificationViewModel
    }
}
