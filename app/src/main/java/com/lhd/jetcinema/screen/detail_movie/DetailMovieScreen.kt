package com.lhd.jetcinema.screen.detail_movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lhd.jetcinema.R
import com.lhd.jetcinema.domain.model.Movie
import com.lhd.jetcinema.util.Constants
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailMovieScreen(
    navigator: DestinationsNavigator,
    movie: Movie?
) {
    Scaffold(
        topBar = {
            AppBarDetailMovie(
                navigateBack = {
                    navigator.popBackStack()
                },
                onClickLike = {}
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AsyncImage(
                    model = "${Constants.BASE_URL_IMAGE}${movie?.posterPath}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = painterResource(R.drawable.ic_placeholder)
                )
            }

            item {
                Text(
                    text = movie?.title ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            item {
                Text(
                    text = movie?.overview ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarDetailMovie(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onClickLike: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = onClickLike
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_heart),
                    contentDescription = null
                )
            }
        }
    )
}

//============================================ PREVIEW =============================================
@Preview
@Composable
fun HeaderDetailPreview() {
    AppBarDetailMovie(
        navigateBack = {},
        onClickLike = {}
    )
}
