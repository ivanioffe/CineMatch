package com.ioffeivan.feature.search_movies.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ioffeivan.feature.search_movies.presentation.composable.SearchMoviesRoute
import kotlinx.serialization.Serializable

@Serializable
data object SearchMoviesRoute

fun NavHostController.navigateToSearchMovies(navOptions: NavOptions? = null) =
    navigate(SearchMoviesRoute, navOptions)

fun NavGraphBuilder.searchMovies(
    onMovieClick: (Int) -> Unit,
) {
    composable<SearchMoviesRoute> {
        SearchMoviesRoute(
            onMovieClick = onMovieClick,
        )
    }
}
