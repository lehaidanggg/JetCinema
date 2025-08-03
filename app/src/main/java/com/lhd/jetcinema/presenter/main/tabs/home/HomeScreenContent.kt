package com.lhd.jetcinema.presenter.main.tabs.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.lhd.jetcinema.R
import com.lhd.jetcinema.domain.model.Genre
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.presenter.common.widgets.BuildError
import com.lhd.jetcinema.presenter.common.widgets.BuildLoading
import com.lhd.jetcinema.presenter.common.widgets.HomeAppBar
import com.lhd.jetcinema.presenter.common.widgets.RateBar
import com.lhd.jetcinema.presenter.theme.Colors
import com.lhd.jetcinema.util.Constants
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    viewmodel: HomeVM = koinViewModel(),
    navigateToDetail: (Movie) -> Unit
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onClickMovie = { movie -> navigateToDetail.invoke(movie) }
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onClickMovie: (Movie) -> Unit
) {
    Scaffold(
        topBar = { HomeAppBar() },
        containerColor = Colors.primary,
    ) { contentPadding ->
        if (uiState.isLoading) {
            // view loading
            BuildLoading(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding)
            )
        } else if (uiState.error != null) {
            // view error
            BuildError(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(contentPadding),
                error = uiState.error
            )
        } else {
            // normal content
            BuildContent(
                modifier = Modifier.padding(contentPadding),
                genres = uiState.categories,
                populars = uiState.populars,
                recommends = uiState.recommends,
                onClickGenre = { },
                onClickMovie = onClickMovie
            )
        }
    }
}

@Composable
private fun BuildContent(
    modifier: Modifier = Modifier,
    genres: ImmutableList<Genre>,
    populars: ImmutableList<Movie>,
    recommends: ImmutableList<Movie>,
    onClickGenre: (Genre) -> Unit,
    onClickMovie: (Movie) -> Unit
) {
    LazyColumn(modifier = modifier) {
        // genres
        item(key = "view_genres") {
            BuildGenres(
                genres = genres,
                onClickGenre = onClickGenre
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        // build iap
        item(key = "view_iap") {
            BuildIap(
                onClickIap = {},
                onClickCancel = {},
            )
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        // build popular
        item(key = "view_popular") {
            BuildPopular(
                movies = populars,
                onClickMovie = onClickMovie
            )
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        // build recommend
        item(key = "view_recommend") {
            BuildPopular(
                movies = recommends,
                onClickMovie = onClickMovie
            )
        }
    }
}

@Composable
private fun BuildPopular(
    movies: ImmutableList<Movie>,
    onClickMovie: (Movie) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Popular",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "See more",
                style = MaterialTheme.typography.labelMedium.copy(
                    textDecoration = TextDecoration.Underline
                )
            )
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(
                items = movies,
                key = { it.id }
            ) { movie ->
                ItemMovie(
                    modifier = Modifier.width(112.dp),
                    movie = movie,
                    onClickMovie = onClickMovie
                )
            }
        }
    }
}

@Composable
private fun BuildGenres(
    genres: ImmutableList<Genre>,
    onClickGenre: (Genre) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = genres,
            key = { genre -> genre.id }
        ) { genre ->
            ItemGenre(
                genre = genre,
                onClickGenre = onClickGenre
            )
        }
    }
}

@Composable
private fun BuildIap(
    modifier: Modifier = Modifier,
    onClickIap: () -> Unit,
    onClickCancel: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Colors.yellow)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Read Anywhere. Anytime.",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Discover, read, listen, and play with ease.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            border = BorderStroke(1.dp, color = Colors.black),
            colors = ButtonColors(
                contentColor = Colors.primary,
                containerColor = Colors.primary,
                disabledContentColor = Colors.black.copy(alpha = 0.4f),
                disabledContainerColor = Colors.black.copy(alpha = 0.4f)
            ),
            shape = RoundedCornerShape(0.dp),
            onClick = { onClickIap.invoke() }
        ) {
            Text(
                text = "Read Free For 30 Days",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.clickable(
                enabled = true,
                onClick = onClickCancel
            ),
            text = "Cancel anytime",
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@Composable
private fun ItemGenre(
    modifier: Modifier = Modifier,
    genre: Genre,
    onClickGenre: (Genre) -> Unit
) {
    Surface(
        modifier = modifier,
        color = Colors.primary,
        border = BorderStroke(width = 1.dp, color = Colors.black),
        onClick = { onClickGenre.invoke(genre) }
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = genre.name,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun ItemMovie(
    modifier: Modifier,
    movie: Movie,
    onClickMovie: (Movie) -> Unit
) {
    val ctx = LocalContext.current
    val url = "${Constants.BASE_URL_IMAGE}${movie.posterPath}"
    val imageRequest = remember(url) {
        ImageRequest.Builder(ctx)
            .data(url)
            .crossfade(true)
            .size(Size.ORIGINAL)
            .build()
    }

    Surface(
        modifier = modifier,
        color = Colors.transparent,
        onClick = { onClickMovie.invoke(movie) }
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .border(width = 1.dp, color = Colors.black)
                    .height(192.dp)
                    .padding(4.dp),
                model = imageRequest,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 16.sp),
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RateBar(countStar = movie.voteAverage.roundToInt())

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_saved),
                    contentDescription = ""
                )
            }
        }
    }
}