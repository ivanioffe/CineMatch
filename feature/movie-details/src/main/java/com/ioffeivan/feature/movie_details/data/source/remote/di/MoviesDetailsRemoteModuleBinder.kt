package com.ioffeivan.feature.movie_details.data.source.remote.di

import com.ioffeivan.feature.movie_details.data.source.remote.data_source.MovieDetailsRemoteDataSource
import com.ioffeivan.feature.movie_details.data.source.remote.data_source.RetrofitMovieDetailsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface MoviesDetailsRemoteModuleBinder {
    @Binds
    fun bindMovieDetailsRemoteDataSource(
        impl: RetrofitMovieDetailsRemoteDataSource,
    ): MovieDetailsRemoteDataSource
}
