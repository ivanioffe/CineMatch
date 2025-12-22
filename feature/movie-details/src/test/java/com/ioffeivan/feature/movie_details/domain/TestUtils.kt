package com.ioffeivan.feature.movie_details.domain

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.core.network.BuildConfig
import com.ioffeivan.feature.movie_details.domain.model.MovieDetails
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.support.ParameterDeclarations
import java.io.IOException
import java.util.stream.Stream

val movieDetailsTest =
    MovieDetails(
        id = 1,
        title = "Title",
        genres = listOf(),
        overview = "",
        year = 2000,
        imageUrl = "${BuildConfig.BACKEND_BASE_URL}images",
    )

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