package com.ioffeivan.core.datastore_auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME: String = "test_datastore"

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class AuthManagerTest {
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile = { testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) },
        )
    private val authManager: AuthManager = AuthManager(testDataStore)

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME).delete()
        testCoroutineScope.cancel()
    }

    @Test
    fun isLoggedIn_whenAccessTokenIsNull_shouldReturnsFalse() {
        testCoroutineScope.runTest {
            val result = authManager.isLoggedIn.first()

            assertThat(result).isFalse()
        }
    }

    @Test
    fun isLoggedIn_whenAccessTokenIsNotNull_shouldReturnsTrue() {
        testCoroutineScope.runTest {
            authManager.saveAccessToken("token")

            val result = authManager.isLoggedIn.first()

            assertThat(result).isTrue()
        }
    }

    @Test
    fun getAccessToken_whenAccessTokenIsNull_shouldReturnsNull() {
        testCoroutineScope.runTest {
            val result = authManager.getAccessToken()

            assertThat(result).isNull()
        }
    }

    @Test
    fun saveAccessToken_whenCalled_shouldSetAccessToken() {
        testCoroutineScope.runTest {
            val expected = "access_token"
            authManager.saveAccessToken(expected)

            val result = authManager.getAccessToken()

            assertThat(result).isEqualTo(expected)
        }
    }

    @Test
    fun deleteAccessToken_whenCalled_shouldSetAccessTokenIsNull() {
        testCoroutineScope.runTest {
            authManager.deleteAccessToken()

            val result = authManager.getAccessToken()

            assertThat(result).isNull()
        }
    }

    @Test
    fun getRefreshToken_whenRefreshTokenIsNull_shouldReturnsNull() {
        testCoroutineScope.runTest {
            val result = authManager.getRefreshToken()

            assertThat(result).isNull()
        }
    }

    @Test
    fun saveRefreshToken_whenCalled_shouldSetRefreshToken() {
        testCoroutineScope.runTest {
            val expected = "refresh_token"
            authManager.saveRefreshToken(expected)

            val result = authManager.getRefreshToken()

            assertThat(result).isEqualTo(expected)
        }
    }

    @Test
    fun deleteRefreshToken_whenCalled_shouldSetRefreshTokenIsNull() {
        testCoroutineScope.runTest {
            authManager.deleteRefreshToken()

            val result = authManager.getRefreshToken()

            assertThat(result).isNull()
        }
    }
}
