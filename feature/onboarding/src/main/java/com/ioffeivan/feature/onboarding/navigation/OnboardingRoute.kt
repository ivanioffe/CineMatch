package com.ioffeivan.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ioffeivan.feature.onboarding.OnboardingRoute
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingRoute

fun NavHostController.navigateToOnboarding(navOptions: NavOptions? = null) =
    navigate(OnboardingRoute, navOptions)

fun NavGraphBuilder.onboarding(
    onLoginButtonClick: () -> Unit,
    onSignupButtonClick: () -> Unit,
) {
    composable<OnboardingRoute> {
        OnboardingRoute(
            onLoginButtonClick = onLoginButtonClick,
            onSignupButtonClick = onSignupButtonClick,
        )
    }
}
