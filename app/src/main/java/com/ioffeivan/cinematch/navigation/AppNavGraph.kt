package com.ioffeivan.cinematch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.auth.presentation.account_created.navigation.navigateToAccountCreated
import com.ioffeivan.feature.auth.presentation.email_verification.navigation.navigateToEmailVerification
import com.ioffeivan.feature.auth.presentation.login.navigation.LoginBaseRoute
import com.ioffeivan.feature.auth.presentation.login.navigation.login
import com.ioffeivan.feature.auth.presentation.login.navigation.navigateToLogin
import com.ioffeivan.feature.auth.presentation.sign_up.navigation.SignUpBaseRoute
import com.ioffeivan.feature.auth.presentation.sign_up.navigation.navigateToSignUp
import com.ioffeivan.feature.auth.presentation.sign_up.navigation.signUp
import com.ioffeivan.feature.movie_details.presentation.navigation.movieDetails
import com.ioffeivan.feature.movie_details.presentation.navigation.navigateToMovieDetails
import com.ioffeivan.feature.onboarding.presentation.navigation.OnboardingRoute
import com.ioffeivan.feature.onboarding.presentation.navigation.onboarding
import com.ioffeivan.feature.search_movies.presentation.navigation.SearchMoviesRoute
import com.ioffeivan.feature.search_movies.presentation.navigation.navigateToSearchMovies
import com.ioffeivan.feature.search_movies.presentation.navigation.searchMovies

@Composable
fun AppNavGraph(
    navController: NavHostController,
    isLoggedIn: Boolean,
    onShowSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) SearchMoviesRoute else OnboardingRoute,
        modifier = modifier,
    ) {
        onboarding(
            onLoginButtonClick = navController::navigateToLogin,
            onSignupButtonClick = navController::navigateToSignUp,
        )

        login(
            onNavigateToSignUp = {
                navController.navigateToSignUp(
                    navOptions =
                        navOptions {
                            popUpTo(SignUpBaseRoute)
                        },
                )
            },
            onNavigateToMain = navController::navigateToSearchMovies,
            onShowSnackbar = onShowSnackbar,
        )

        signUp(
            onNavigateToLogin = {
                navController.navigateToLogin(
                    navOptions =
                        navOptions {
                            popUpTo(LoginBaseRoute)
                        },
                )
            },
            onNavigateToVerifyEmail = navController::navigateToEmailVerification,
            onNavigateToAccountCreated = navController::navigateToAccountCreated,
            onShowSnackbar = onShowSnackbar,
        )

        searchMovies(
            onMovieClick = navController::navigateToMovieDetails,
        )

        movieDetails(
            onBackClick = navController::popBackStack,
        )
    }
}
