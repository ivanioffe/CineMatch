package com.ioffeivan.core.database.di

import com.ioffeivan.core.database.AppDatabase
import com.ioffeivan.core.database.dao.MovieSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideMovieSearchDao(
        database: AppDatabase,
    ): MovieSearchDao = database.movieSearchDao()
}
