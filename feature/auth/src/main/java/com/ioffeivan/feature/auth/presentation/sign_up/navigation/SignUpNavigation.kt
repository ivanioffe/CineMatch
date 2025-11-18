package com.ioffeivan.feature.auth.presentation.sign_up.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.auth.presentation.sign_up.composable.SignUpRoute
import kotlinx.serialization.Serializable

@Serializable
data object SignUpRoute

fun NavHostController.navigateToSignUp(navOptions: NavOptions? = null) =
    navigate(SignUpRoute, navOptions)

fun NavGraphBuilder.signUp(
    onNavigateToLogin: () -> Unit,
    onNavigateToVerifyEmail: () -> Unit,
    onShowSnackbar: ShowSnackbar,
) {
    composable<SignUpRoute> {
        SignUpRoute(
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToVerifyEmail = onNavigateToVerifyEmail,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
