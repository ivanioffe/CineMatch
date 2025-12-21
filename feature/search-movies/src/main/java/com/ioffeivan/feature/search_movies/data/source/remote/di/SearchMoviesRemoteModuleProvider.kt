package com.ioffeivan.feature.search_movies.data.source.remote.di

import com.ioffeivan.core.network.di.Authorized
import com.ioffeivan.feature.search_movies.data.source.remote.api.SearchMoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class SearchMoviesRemoteModuleProvider {
    @Provides
    @Singleton
    fun provideSearchMoviesApiService(
        @Authorized retrofit: Retrofit,
    ): SearchMoviesApiService {
        return retrofit.create()
    }
}
