package com.ioffeivan.cinematch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ioffeivan.cinematch.ui.AppScreen
import com.ioffeivan.core.designsystem.theme.CineMatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CineMatchTheme {
                Surface {
                    val uiState by mainActivityViewModel.uiState.collectAsStateWithLifecycle()

                    if (uiState !is MainActivityUiState.Loading) {
                        AppScreen(
                            isLoggedIn = uiState is MainActivityUiState.LoggedIn,
                        )
                    }
                }
            }
        }
    }
}
