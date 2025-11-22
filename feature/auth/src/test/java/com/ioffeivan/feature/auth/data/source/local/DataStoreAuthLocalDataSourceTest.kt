package com.ioffeivan.feature.auth.data.source.local

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.datastore_auth.AuthManager
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DataStoreAuthLocalDataSourceTest {
    private lateinit var authManager: AuthManager
    private lateinit var dataSource: AuthLocalDataSource

    @BeforeEach
    fun setUp() {
        authManager = mockk(relaxed = true)
        dataSource = DataStoreAuthLocalDataSource(authManager)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun isLoggedIn_whenLocalDataSourceReturnsFalse_shouldReturnsFalse() =
        runTest {
            val expected = false
            every { authManager.isLoggedIn } returns flowOf(expected)

            val actual = dataSource.isLoggedIn.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { authManager.isLoggedIn }
        }

    @Test
    fun isLoggedIn_whenLocalDataSourceReturnsTrue_shouldReturnsTrue() =
        runTest {
            val expected = true
            every { authManager.isLoggedIn } returns flowOf(expected)

            val actual = dataSource.isLoggedIn.first()

            assertThat(actual).isEqualTo(expected)
            verify(exactly = 1) { authManager.isLoggedIn }
        }

    @Test
    fun saveAccessToken_shouldDelegateToLocalDataSource() {
        runTest {
            val accessToken = "access_token"
            dataSource.saveAccessToken(accessToken)

            coVerify(exactly = 1) { authManager.saveAccessToken(accessToken) }
        }
    }

    @Test
    fun saveRefreshToken_shouldDelegateToLocalDataSource() {
        runTest {
            val refreshToken = "refresh_token"
            dataSource.saveRefreshToken(refreshToken)

            coVerify(exactly = 1) { authManager.saveRefreshToken(refreshToken) }
        }
    }
}
