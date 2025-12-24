package com.ioffeivan.feature.search_movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ioffeivan.feature.search_movies.domain.model.SearchMovie
import com.ioffeivan.feature.search_movies.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val pagingState: Flow<PagingData<SearchMovie>> =
        _query
            .debounce(500L)
            .filter { it.isNotBlank() }
            .distinctUntilChanged { old, new -> old.trim() == new.trim() }
            .flatMapLatest {
                searchMoviesUseCase(it)
            }
            .cachedIn(viewModelScope)

    fun onQueryChange(query: String) {
        _query.update { query }
    }
}
