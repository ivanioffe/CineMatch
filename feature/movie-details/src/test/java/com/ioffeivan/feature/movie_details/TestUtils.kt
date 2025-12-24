package com.ioffeivan.feature.movie_details

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.BuildConfig
import com.ioffeivan.feature.movie_details.data.source.remote.model.MovieDetailsDto
import com.ioffeivan.feature.movie_details.domain.model.MovieDetails
import com.ioffeivan.feature.movie_details.presentation.MovieDetailsState
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.support.ParameterDeclarations
import java.io.IOException
import java.util.stream.Stream

val movieDetailsDtoTest =
    MovieDetailsDto(
        id = 1,
        title = "Title",
        genres = listOf("Genre1", "Genre2", "Genre3"),
        overview = "overview",
        year = 2000,
        imageUrl = "",
    )

val movieDetailsTest =
    MovieDetails(
        id = 1,
        title = "Title",
        genres = listOf("Genre1", "Genre2", "Genre3"),
        overview = "overview",
        year = 2000,
        imageUrl = "${BuildConfig.BACKEND_BASE_URL}images",
    )

val movieDetailsStateTest =
    MovieDetailsState(
        id = 1,
        title = "Title",
        genres = "Genre1, Genre2, Genre3",
        overview = "overview",
        year = "2000",
        imageUrl = "${BuildConfig.BACKEND_BASE_URL}images",
        isLoading = false,
        isError = false,
    )

class GetMovieDetailsResultDtoArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(
        parameters: ParameterDeclarations?,
        context: ExtensionContext?,
    ): Stream<out Arguments?>? {
        return Stream.of(
            Arguments.of(
                Result.Success(movieDetailsDtoTest),
            ),
            Arguments.of(Result.Error("error")),
            Arguments.of(Result.Exception(IOException())),
        )
    }
}

class GetMovieDetailsResultDomainArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(
        parameters: ParameterDeclarations?,
        context: ExtensionContext?,
    ): Stream<out Arguments?>? {
        return Stream.of(
            Arguments.of(
                Result.Success(movieDetailsTest),
            ),
            Arguments.of(Result.Error("error")),
            Arguments.of(Result.Exception(IOException())),
        )
    }
}
