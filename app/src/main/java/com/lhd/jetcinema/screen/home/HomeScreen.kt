package com.lhd.jetcinema.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lhd.jetcinema.R
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.domain.model.TypeScan
import com.lhd.jetcinema.screen.common.widgets.MyErrorView
import com.lhd.jetcinema.screen.common.widgets.MyLoadingView
import com.lhd.jetcinema.screen.home.HomeVM.MovieUiState
import com.lhd.jetcinema.screen.home.HomeVM.SliderUiState
import com.lhd.jetcinema.screen.theme.Colors
import com.lhd.jetcinema.util.Constants
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewmodel: HomeVM = koinViewModel(),
    navigateScanFile: (TypeScan) -> Unit
) {
    val sliderState by viewmodel.sliderUiState.collectAsStateWithLifecycle()
    val nowPlaying by viewmodel.nowPlayingUiState.collectAsStateWithLifecycle()
    val popularMovie by viewmodel.popularUiState.collectAsStateWithLifecycle()

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
            item {
                SliderContent(sliderState = sliderState)
            }

            item {
                NowPlayingContent(nowPlayingState = nowPlaying)
            }

            item {
                PopularContent(popularState = popularMovie)
            }
        }
    }
}

@Composable
fun NowPlayingContent(
    nowPlayingState: MovieUiState
) {
    when (val state = nowPlayingState) {
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
                HeaderItem(title = "Now Playing")

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
                        MovieItem(
                            modifier = Modifier.width(140.dp),
                            movie = movie
                        )
                    }
                }
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
                        MovieItem(
                            modifier = Modifier.width(140.dp),
                            movie = movie
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape),
            painter = painterResource(R.drawable.img_avatar),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "Hi, LHD",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W600
                )
            )
            Text(
                text = "Let's watch a movie",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(R.drawable.ic_notification),
            contentDescription = null
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SliderContent(
    modifier: Modifier = Modifier,
    sliderState: SliderUiState,
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

            BoxWithConstraints {
                val parentWidth: Dp = maxWidth
                val widthText = parentWidth * 2f / 3f

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
                        widthText = widthText
                    )
                }
            }
        }
    }
}

@Composable
fun SliderItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    widthText: Dp
) {
    Surface(
        modifier = modifier.clip(RoundedCornerShape(20.dp)),
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
            )

            Box(
                modifier = Modifier
                    .width(widthText)
                    .fillMaxHeight()
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
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("${Constants.BASE_URL_IMAGE}${movie.posterPath}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun HeaderItem(
    modifier: Modifier = Modifier,
    title: String
) {
    Box(
        modifier = modifier.wrapContentWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W700
            ),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Center)
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
        sliderState = SliderUiState.Loading
    )
}