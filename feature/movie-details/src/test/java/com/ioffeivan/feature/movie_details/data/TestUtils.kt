package com.ioffeivan.feature.movie_details.data

import com.ioffeivan.core.common.result.Result
import com.ioffeivan.feature.movie_details.data.source.remote.model.MovieDetailsDto
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
        genres = listOf(),
        overview = "",
        year = 2000,
        imageUrl = "",
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
