package com.ioffeivan.feature.auth.presentation.account_created.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ioffeivan.feature.auth.presentation.account_created.AccountCreatedScreen
import kotlinx.serialization.Serializable

@Serializable
data object AccountCreatedRoute

fun NavController.navigateToAccountCreated(navOptions: NavOptions? = null) =
    navigate(AccountCreatedRoute, navOptions)

fun NavGraphBuilder.accountCreated(
    onLoginClick: () -> Unit,
) {
    composable<AccountCreatedRoute> {
        AccountCreatedScreen(
            onLoginClick = onLoginClick,
        )
    }
}
