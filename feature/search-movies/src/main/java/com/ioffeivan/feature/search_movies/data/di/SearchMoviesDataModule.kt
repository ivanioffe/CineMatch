package com.ioffeivan.feature.search_movies.data.di

import com.ioffeivan.feature.search_movies.data.repository.SearchMoviesRepositoryImpl
import com.ioffeivan.feature.search_movies.domain.repository.SearchMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SearchMoviesDataModule {
    @Binds
    fun bindSearchMoviesRepository(
        impl: SearchMoviesRepositoryImpl,
    ): SearchMoviesRepository
}
