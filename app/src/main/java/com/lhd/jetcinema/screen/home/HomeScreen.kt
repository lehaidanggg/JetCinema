package com.lhd.jetcinema.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lhd.jetcinema.R
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.screen.common.widgets.HomeAppBar
import com.lhd.jetcinema.screen.common.widgets.MyErrorView
import com.lhd.jetcinema.screen.common.widgets.MyLoadingView
import com.lhd.jetcinema.screen.destinations.DetailMovieScreenDestination
import com.lhd.jetcinema.screen.home.HomeVM.MovieUiState
import com.lhd.jetcinema.screen.home.HomeVM.SliderUiState
import com.lhd.jetcinema.screen.theme.Colors
import com.lhd.jetcinema.util.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination(start = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewmodel: HomeVM = koinViewModel(),
    navigator: DestinationsNavigator,
) {
    val sliderState by viewmodel.sliderUiState.collectAsStateWithLifecycle()
    val popularState by viewmodel.popularUiState.collectAsStateWithLifecycle()
    val topRatedState by viewmodel.topRatedUiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { HomeAppBar() },
        containerColor = Colors.white,
        modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier.padding(contentPadding),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // SLIDER
            item {
                SliderContent(
                    sliderState = sliderState,
                    onClickMovie = { movie ->
                        navigator.navigate(DetailMovieScreenDestination(movie))
                    }
                )
            }

            // RECOMMENDED FOR YOU
            item {
                PopularContent(popularState = popularState)
            }

            // TOP SEARCHED
            topRatedContent(topRatedState = topRatedState)()
        }
    }
}

fun topRatedContent(
    topRatedState: MovieUiState,
): LazyListScope.() -> Unit = {
    when (val state = topRatedState) {
        MovieUiState.Loading -> {
            item { MyLoadingView() }
        }

        is MovieUiState.Error -> {
            item { MyErrorView(error = state.error.message.toString()) }
        }

        is MovieUiState.Success -> {
            item {
                HeaderItem(title = "Top Searches")
            }

            items(
                items = state.data,
                key = { it.id }
            ) { movie ->
                MovieItemVertical(
                    movie = movie,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
            }
        }
    }
}

@Composable
fun PopularContent(
    popularState: MovieUiState
) {
    when (val state = popularState) {
        MovieUiState.Loading -> {
            MyLoadingView()
        }

        is MovieUiState.Error -> {
            MyErrorView(
                error = state.error.message.toString()
            )
        }

        is MovieUiState.Success -> {
            Column {
                HeaderItem(title = "Popular")

                Spacer(modifier = Modifier.height(6.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(216.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.data,
                        key = { item -> item.id }
                    ) { movie ->
                        MovieItemHorizontal(
                            modifier = Modifier.width(140.dp),
                            movie = movie
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SliderContent(
    modifier: Modifier = Modifier,
    sliderState: SliderUiState,
    onClickMovie: (Movie) -> Unit,
) {
    when (sliderState) {
        SliderUiState.Loading -> {
            MyLoadingView()
        }

        is SliderUiState.Error -> {
            MyErrorView(
                error = sliderState.error.message.toString()
            )
        }

        is SliderUiState.Success -> {
            val movies = sliderState.data
            val pagerState = rememberPagerState(
                pageCount = { movies.size }
            )
            HorizontalPager(
                modifier = modifier.height(172.dp),
                state = pagerState,
                pageSize = PageSize.Fill,
                contentPadding = PaddingValues(horizontal = 12.dp),
                pageSpacing = 8.dp
            ) { index ->
                val movie = movies[index]
                SliderItem(
                    movie = movie,
                    onClickMovie = onClickMovie
                )
            }
        }
    }
}

@Composable
fun SliderItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClickMovie: (Movie) -> Unit
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
        onClick = {
            onClickMovie.invoke(movie)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            AsyncImage(
                model = "${Constants.BASE_URL_IMAGE}${movie.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.ic_placeholder)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Colors.primary, Colors.transparent),
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Colors.white,
                            fontWeight = FontWeight.W600
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = movie.overview,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Colors.grayScale10,
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        colors = ButtonColors(
                            containerColor = Colors.white,
                            contentColor = Colors.primary,
                            disabledContentColor = Colors.grayScale40,
                            disabledContainerColor = Colors.grayScale40
                        ),
                        onClick = {
                            // TODO: handle click watch now
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(0.dp),
                            text = "Watch now",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItemHorizontal(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${Constants.BASE_URL_IMAGE}${movie.posterPath}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_placeholder),
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Colors.black,
                fontWeight = FontWeight.W700
            )
        )
    }
}

@Composable
fun MovieItemVertical(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(148.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${Constants.BASE_URL_IMAGE}${movie.posterPath}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_placeholder),
            modifier = Modifier
                .fillMaxHeight()
                .width(98.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Colors.black,
                fontWeight = FontWeight.W700
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            modifier = Modifier.padding(8.dp),
            painter = painterResource(R.drawable.ic_play_black),
            contentDescription = "icon play movie vertical"
        )
    }
}

@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
        )
        Text(
            text = "See all",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Colors.primary
            ),
            modifier = Modifier
                .padding(horizontal = 12.dp)
        )
    }
}


// =================================== PREVIEW ====================================
@Preview
@Composable
fun HomeAppBarPreview() {
    HomeAppBar()
}

@Preview
@Composable
fun SliderContentPreview() {
    SliderContent(
        sliderState = SliderUiState.Loading,
        onClickMovie = {}
    )
}