package com.lhd.jetcinema.presenter.detail_movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lhd.jetcinema.R
import com.lhd.jetcinema.presenter.theme.Colors
import com.lhd.jetcinema.util.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailMovieScreen(
    detailVM: DetailVM = koinViewModel(),
    navigateBack: () -> Unit
) {
    val detailUiState by detailVM.uiState.collectAsStateWithLifecycle()
    val uiEvent by detailVM.uiEvent.collectAsStateWithLifecycle(DetailMovieEvent.Idle)

    LaunchedEffect(uiEvent) {
        when (uiEvent) {
            DetailMovieEvent.OnBackPress -> navigateBack.invoke()
            else -> {}
        }
    }

    DetailContent(
        uiState = detailUiState,
        navigateBack = { detailVM.sendEvent(DetailMovieEvent.OnBackPress) }
    )
}

@Composable
private fun DetailContent(
    uiState: DetailMovieState,
    navigateBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.white)
    ) { contentPadding ->
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .drawWithContent {
                        drawContent()
//                        drawRect(
//                            color = Colors.grayScale70.copy(
//                                alpha = 0.4f
//                            )
//                        )
                    },
                model = "${Constants.BASE_URL_IMAGE}${uiState.movie?.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_placeholder)
            )
            Column(
                modifier = Modifier.padding(contentPadding)
            ) {
                AppBarDetailMovie(
                    title = uiState.movie?.title ?: "",
                    navigateBack = { navigateBack.invoke() },
                    onClickLike = {}
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = uiState.movie?.title ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    item {
                        Text(
                            text = uiState.movie?.overview ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppBarDetailMovie(
    modifier: Modifier = Modifier,
    title: String,
    navigateBack: () -> Unit,
    onClickLike: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = navigateBack
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                tint = Colors.white,
                contentDescription = null
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = Colors.white
            )
        )

        IconButton(
            onClick = onClickLike
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_heart),
                tint = Colors.white,
                contentDescription = null
            )
        }
    }
}