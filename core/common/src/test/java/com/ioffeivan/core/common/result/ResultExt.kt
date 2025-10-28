package com.ioffeivan.core.common.result

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.io.IOException

class ResultExt {
    @Test
    fun onSuccess_whenResultIsSuccess_shouldExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Success("data")

            result.onSuccess {
                count++
            }

            assertThat(count).isEqualTo(1)
        }

    @Test
    fun onSuccess_whenResultIsError_shouldNotExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Error("message")

            result.onSuccess {
                count++
            }

            assertThat(count).isEqualTo(0)
        }

    @Test
    fun onSuccess_whenResultIsException_shouldNotExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Exception(IOException())

            result.onSuccess {
                count++
            }

            assertThat(count).isEqualTo(0)
        }

    @Test
    fun onError_whenResultIsError_shouldExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Error("message")

            result.onError {
                count++
            }

            assertThat(count).isEqualTo(1)
        }

    @Test
    fun onError_whenResultIsSuccess_shouldNotExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Success("data")

            result.onError {
                count++
            }

            assertThat(count).isEqualTo(0)
        }

    @Test
    fun onError_whenResultIsException_shouldNotExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Exception(IOException())

            result.onError {
                count++
            }

            assertThat(count).isEqualTo(0)
        }

    @Test
    fun onException_whenResultIsException_shouldExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Exception(Exception())

            result.onException {
                count++
            }

            assertThat(count).isEqualTo(1)
        }

    @Test
    fun onException_whenResultIsSuccess_shouldNotExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Success("data")

            result.onException {
                count++
            }

            assertThat(count).isEqualTo(0)
        }

    @Test
    fun onException_whenResultIsError_shouldNotExecuteLambda() =
        runTest {
            var count = 0
            val result = Result.Error("message")

            result.onException {
                count++
            }

            assertThat(count).isEqualTo(0)
        }
}
