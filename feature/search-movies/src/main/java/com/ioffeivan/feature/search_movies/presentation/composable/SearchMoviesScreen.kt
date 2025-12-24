package com.ioffeivan.feature.search_movies.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ioffeivan.core.ui.ErrorScreen
import com.ioffeivan.feature.search_movies.R
import com.ioffeivan.feature.search_movies.domain.model.SearchMovie
import com.ioffeivan.feature.search_movies.presentation.SearchMoviesViewModel

@Composable
internal fun SearchMoviesRoute(
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit,
    viewModel: SearchMoviesViewModel = hiltViewModel(),
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val pagingState = viewModel.pagingState.collectAsLazyPagingItems()

    SearchMoviesScreen(
        query = query,
        pagingState = pagingState,
        onQueryChange = viewModel::onQueryChange,
        onMovieClick = onMovieClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchMoviesScreen(
    query: String,
    pagingState: LazyPagingItems<SearchMovie>,
    onQueryChange: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            )
        },
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
        ) {
            if (query.isBlank()) {
                Text(text = stringResource(R.string.search_initial_prompt))
            } else {
                if (pagingState.itemCount > 0) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding =
                            PaddingValues(
                                vertical = 16.dp,
                            ),
                        modifier =
                            Modifier
                                .fillMaxSize(),
                    ) {
                        items(
                            count = pagingState.itemCount,
                        ) {
                            val movie = pagingState[it]
                            movie?.let {
                                SearchMoviesItem(
                                    searchMovie = movie,
                                    onClick = onMovieClick,
                                )
                            }
                        }

                        item {
                            val appendState = pagingState.loadState.append

                            Box(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(),
                            ) {
                                when (appendState) {
                                    is LoadState.Loading -> {
                                        CircularProgressIndicator(
                                            modifier =
                                                Modifier
                                                    .align(Alignment.Center),
                                        )
                                    }

                                    is LoadState.Error -> {
                                        ErrorScreen(
                                            onRetryClick = {
                                                pagingState.retry()
                                            },
                                            modifier =
                                                Modifier
                                                    .align(Alignment.Center),
                                        )
                                    }

                                    is LoadState.NotLoading -> {}
                                }
                            }
                        }
                    }
                } else {
                    when (pagingState.loadState.refresh) {
                        LoadState.Loading -> {
                            CircularProgressIndicator()
                        }

                        is LoadState.Error -> {
                            ErrorScreen(
                                onRetryClick = {
                                    pagingState.retry()
                                },
                            )
                        }

                        is LoadState.NotLoading -> {
                            Text(
                                text = stringResource(R.string.search_no_results),
                            )
                        }
                    }
                }
            }
        }
    }
}
