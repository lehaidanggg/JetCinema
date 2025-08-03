package com.lhd.jetcinema.presenter.common.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lhd.jetcinema.R

@Composable
fun RateBar(
    modifier: Modifier = Modifier,
    countStar: Int
) {
    assert(countStar <= 10) {
        throw Exception("count rate must <= 10")
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        repeat(countStar / 2) {
            Image(
                modifier = Modifier.size(12.dp),
                painter = painterResource(R.drawable.ic_star),
                contentDescription = "star"
            )
        }
    }
}