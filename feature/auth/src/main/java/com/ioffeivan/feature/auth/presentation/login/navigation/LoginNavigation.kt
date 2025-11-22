package com.ioffeivan.feature.auth.presentation.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.auth.presentation.login.composable.LoginRoute
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavHostController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(LoginRoute, navOptions)

fun NavGraphBuilder.login(
    onNavigateToSignUp: () -> Unit,
    onNavigateToMain: () -> Unit,
    onShowSnackbar: ShowSnackbar,
) {
    composable<LoginRoute> {
        LoginRoute(
            onNavigateToSignUp = onNavigateToSignUp,
            onNavigateToMain = onNavigateToMain,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
