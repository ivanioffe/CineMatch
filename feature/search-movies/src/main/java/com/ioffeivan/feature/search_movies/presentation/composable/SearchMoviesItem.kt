package com.ioffeivan.feature.search_movies.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.ui.onDebounceClick
import com.ioffeivan.feature.search_movies.domain.model.SearchMovie

@Composable
internal fun SearchMoviesItem(
    searchMovie: SearchMovie,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable(
                    onClick =
                        onDebounceClick {
                            onClick(searchMovie.id)
                        },
                ),
    ) {
        AsyncImage(
            model = searchMovie.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(width = 125.dp, height = 100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray),
        )

        Column(
            modifier =
                Modifier
                    .padding(vertical = 2.dp),
        ) {
            Text(
                text = searchMovie.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style =
                    MaterialTheme.typography.titleMedium
                        .copy(
                            fontWeight = FontWeight.Bold,
                        ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = searchMovie.genres.joinToString(", "),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style =
                    MaterialTheme.typography.bodyMedium
                        .copy(
                            fontWeight = FontWeight.Light,
                        ),
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = searchMovie.year.toString(),
                style =
                    MaterialTheme.typography.bodySmall
                        .copy(
                            fontWeight = FontWeight.Light,
                        ),
            )
        }
    }
}

@Preview
@Composable
private fun SearchMoviesItemPreview() {
    PreviewContainer {
        SearchMoviesItem(
            searchMovie =
                SearchMovie(
                    id = 1,
                    title = "Название",
                    genres = listOf("Жанр1, Жанр2"),
                    year = 2000,
                    imageUrl = "",
                ),
            onClick = {},
        )
    }
}
