package com.ioffeivan.feature.movie_details.presentation.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ioffeivan.feature.movie_details.presentation.MovieDetailsViewModel
import com.ioffeivan.feature.movie_details.presentation.composable.MovieDetailsRoute
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsRoute(
    val movieId: Int,
)

fun NavHostController.navigateToMovieDetails(
    movieId: Int,
    navOptions: NavOptions? = null,
) =
    navigate(
        route = MovieDetailsRoute(movieId),
        navOptions = navOptions,
    )

fun NavGraphBuilder.movieDetails(
    onBackClick: () -> Unit,
) {
    composable<MovieDetailsRoute> { backStackEntry ->
        val (movieId) = backStackEntry.toRoute<MovieDetailsRoute>()
        val creationCallback: (MovieDetailsViewModel.Factory) -> MovieDetailsViewModel =
            { factory ->
                factory.create(movieId)
            }

        MovieDetailsRoute(
            onBackClick = onBackClick,
            viewModel =
                hiltViewModel(
                    key = "$movieId",
                    creationCallback = creationCallback,
                ),
        )
    }
}
