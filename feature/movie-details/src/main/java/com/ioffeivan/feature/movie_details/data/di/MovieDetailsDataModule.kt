package com.ioffeivan.feature.movie_details.data.di

import com.ioffeivan.feature.movie_details.data.repository.MovieDetailsRepositoryImpl
import com.ioffeivan.feature.movie_details.domain.repository.MovieDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MovieDetailsDataModule {
    @Binds
    fun bindMovieDetailsRepository(
        impl: MovieDetailsRepositoryImpl,
    ): MovieDetailsRepository
}
