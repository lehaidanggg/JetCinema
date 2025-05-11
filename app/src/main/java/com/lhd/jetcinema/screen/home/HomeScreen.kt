package com.lhd.jetcinema.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lhd.jetcinema.R
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.domain.model.TypeScan
import com.lhd.jetcinema.screen.common.widgets.MyErrorView
import com.lhd.jetcinema.screen.common.widgets.MyLoadingView
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

    Scaffold(
        topBar = { HomeAppBar() },
        containerColor = Colors.white,
        modifier = Modifier
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) { contentPadding ->

        SliderContent(
            modifier = Modifier.padding(contentPadding),
            sliderState = sliderState
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
            MyLoadingView(
                modifier = modifier
            )
        }

        is SliderUiState.Error -> {
            MyErrorView(
                modifier = modifier,
                error = sliderState.error.message.toString()
            )
        }

        is SliderUiState.Success -> {
            val movies = sliderState.data
            val pagerState = rememberPagerState(
                pageCount = { movies.size }
            )

            Column {
                BoxWithConstraints(
                    modifier = modifier
                ) {
                    val parentHeight: Dp = maxHeight
                    val parentWidth: Dp = maxWidth
                    val heightPager = parentHeight * 1f / 4f
                    val widthText = parentWidth * 2f / 3f

                    HorizontalPager(
                        modifier = Modifier.height(heightPager),
                        state = pagerState,
                        pageSize = PageSize.Fill,
                    ) { index ->
                        val movie = movies[index]
                        SliderItem(
                            modifier = modifier,
                            movie = movie,
                            widthText = widthText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    repeat(movies.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .width(if (isSelected) 24.dp else 6.dp)
                                .height(6.dp)
                                .background(
                                    color = if (isSelected) Colors.primary else Colors.grayScale70,
                                    shape = CircleShape
                                )
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


@Composable
fun SliderItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    widthText: Dp
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(20.dp))
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

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .width(widthText)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Colors.primary, Colors.transparent),
                        )
                    )
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