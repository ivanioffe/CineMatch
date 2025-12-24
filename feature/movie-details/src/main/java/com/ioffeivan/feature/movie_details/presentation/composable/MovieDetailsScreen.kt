package com.ioffeivan.feature.movie_details.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.ui.ErrorScreen
import com.ioffeivan.core.ui.ObserveEffectsWithLifecycle
import com.ioffeivan.feature.movie_details.presentation.MovieDetailsEffect
import com.ioffeivan.feature.movie_details.presentation.MovieDetailsEvent
import com.ioffeivan.feature.movie_details.presentation.MovieDetailsState
import com.ioffeivan.feature.movie_details.presentation.MovieDetailsViewModel

@Composable
internal fun MovieDetailsRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect,
        onEffect = { effect ->
            when (effect) {
                MovieDetailsEffect.NavigateToBack ->
                    onBackClick()
            }
        },
    )

    MovieDetailsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MovieDetailsScreen(
    state: MovieDetailsState,
    onEvent: (MovieDetailsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(MovieDetailsEvent.BackClick)
                        },
                    ) {
                        PrimaryIcon(
                            id = PrimaryIcons.ArrowBack,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .align(Alignment.Center),
                    )
                }

                state.isError -> {
                    ErrorScreen(
                        onRetryClick = {
                            onEvent(MovieDetailsEvent.RetryLoadClick)
                        },
                        modifier =
                            Modifier
                                .align(Alignment.Center),
                    )
                }

                else -> {
                    MovieDetailsContent(
                        state = state,
                        modifier =
                            Modifier
                                .padding(bottom = 16.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieDetailsContent(
    state: MovieDetailsState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        AsyncImage(
            model = state.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(400.dp),
        )

        Column(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp),
        ) {
            Text(
                text = state.title,
                style =
                    MaterialTheme.typography.titleLarge
                        .copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = state.genres,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = state.year,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = state.overview,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
fun MovieDetailsScreenContentPreview() {
    PreviewContainer {
        MovieDetailsScreen(
            state =
                MovieDetailsState(
                    id = 0,
                    title = "Название",
                    genres = "Жанр1, Жанр2, Жанр3",
                    overview =
                        "Этот «фильм» — о поиске смысла в хаосе, о борьбе света и тени, и о том, что каждый человек является режиссером собственной судьбы.",
                    year = "2000",
                    imageUrl = "",
                    isLoading = false,
                    isError = false,
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
fun MovieDetailsScreenLoadingPreview() {
    PreviewContainer {
        MovieDetailsScreen(
            state = MovieDetailsState.initial(),
            onEvent = {},
        )
    }
}

@Preview
@Composable
fun MovieDetailsScreenErrorPreview() {
    PreviewContainer {
        MovieDetailsScreen(
            state =
                MovieDetailsState.initial().copy(
                    isLoading = false,
                    isError = true,
                ),
            onEvent = {},
        )
    }
}
