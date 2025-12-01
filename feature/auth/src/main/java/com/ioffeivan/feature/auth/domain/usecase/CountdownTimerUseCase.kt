package com.ioffeivan.feature.auth.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountdownTimerUseCase @Inject constructor() {
    operator fun invoke(seconds: Int): Flow<Int> =
        flow {
            for (time in seconds downTo 0) {
                emit(time)

                if (time > 0) {
                    delay(1000L)
                }
            }
        }
}
