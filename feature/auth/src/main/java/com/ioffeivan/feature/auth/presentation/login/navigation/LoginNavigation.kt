package com.ioffeivan.feature.auth.presentation.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.auth.presentation.login.composable.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginBaseRoute

@Serializable
internal data object LoginRoute

fun NavHostController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(LoginBaseRoute, navOptions)

fun NavGraphBuilder.login(
    onNavigateToSignUp: () -> Unit,
    onNavigateToMain: () -> Unit,
    onShowSnackbar: ShowSnackbar,
) {
    navigation<LoginBaseRoute>(
        startDestination = LoginRoute,
    ) {
        composable<LoginRoute> {
            LoginRoute(
                onNavigateToSignUp = onNavigateToSignUp,
                onNavigateToMain = onNavigateToMain,
                onShowSnackbar = onShowSnackbar,
            )
        }
    }
}
