package com.lhd.jetcinema.screen.detail_movie

import android.util.Log
import androidx.compose.runtime.Composable
import com.lhd.jetcinema.domain.model.Movie
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DetailMovieScreen(
    movie: Movie?
) {
    Log.e("QWfqwfq", "aaaaaaa: $movie")
}