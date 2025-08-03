package com.lhd.jetcinema.presenter.common.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lhd.jetcinema.R


@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .systemBarsPadding(),
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
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_notification),
            contentDescription = null
        )
    }
}