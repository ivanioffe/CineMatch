package com.ioffeivan.feature.movie_details.data.source.remote.di

import com.ioffeivan.core.network.di.Authorized
import com.ioffeivan.feature.movie_details.data.source.remote.api.MovieDetailsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MoviesDetailsRemoteModuleProvider {
    @Provides
    @Singleton
    fun provideMovieDetailsApiService(
        @Authorized retrofit: Retrofit,
    ): MovieDetailsApiService {
        return retrofit.create()
    }
}
