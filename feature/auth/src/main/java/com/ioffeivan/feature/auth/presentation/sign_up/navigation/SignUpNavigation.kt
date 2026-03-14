package com.ioffeivan.feature.auth.presentation.sign_up.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.auth.presentation.account_created.navigation.accountCreated
import com.ioffeivan.feature.auth.presentation.email_verification.navigation.emailVerification
import com.ioffeivan.feature.auth.presentation.sign_up.composable.SignUpRoute
import kotlinx.serialization.Serializable

@Serializable
data object SignUpBaseRoute

@Serializable
internal data object SignUpRoute

fun NavHostController.navigateToSignUp(navOptions: NavOptions? = null) =
    navigate(SignUpBaseRoute, navOptions)

fun NavGraphBuilder.signUp(
    onNavigateToLogin: () -> Unit,
    onNavigateToVerifyEmail: (String) -> Unit,
    onNavigateToAccountCreated: () -> Unit,
    onShowSnackbar: ShowSnackbar,
) {
    navigation<SignUpBaseRoute>(
        startDestination = SignUpRoute,
    ) {
        composable<SignUpRoute> {
            SignUpRoute(
                onNavigateToLogin = onNavigateToLogin,
                onNavigateToVerifyEmail = onNavigateToVerifyEmail,
                onShowSnackbar = onShowSnackbar,
            )
        }

        emailVerification(
            onNavigateToAccountCreated = onNavigateToAccountCreated,
            onShowSnackbar = onShowSnackbar,
        )

        accountCreated(
            onLoginClick = onNavigateToLogin,
        )
    }
}
