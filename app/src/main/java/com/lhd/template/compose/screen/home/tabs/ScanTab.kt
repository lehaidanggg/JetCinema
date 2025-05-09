package com.lhd.template.compose.screen.home.tabs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lhd.template.compose.R
import com.lhd.template.compose.data.model.TypeScan
import com.lhd.template.compose.screen.theme.Colors
import com.lhd.template.compose.screen.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanTab(
    navigateScanFile: (TypeScan) -> Unit
) {
    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(color = Colors.White),
            contentAlignment = Alignment.TopCenter
        ) {
            val indexSelect = remember { mutableIntStateOf(-1) }
            val paddingCard = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)

            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = "Home",
                    style = MaterialTheme.typography.titleLarge.copy(color = Colors.Black),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 4.dp),
                    text = "Choose what you want to recover. ",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Colors.Black),
                    textAlign = TextAlign.Start
                )

                //==================================================================
                CardItem(
                    modifier = paddingCard,
                    resIcon = R.drawable.ic_recover_img,
                    title = "Photo",
                    isSelected = (indexSelect.intValue == 0),
                    onClick = {
                        navigateScanFile(TypeScan.PHOTO)
                        indexSelect.intValue = 0
                    }
                )
                CardItem(
                    modifier = paddingCard,
                    resIcon = R.drawable.ic_recover_video,
                    title = "Video",
                    isSelected = (indexSelect.intValue == 1),
                    onClick = {
                        navigateScanFile(TypeScan.VIDEO)
                        indexSelect.intValue = 1
                    }
                )
                CardItem(
                    modifier = paddingCard,
                    resIcon = R.drawable.ic_recover_file,
                    title = "Other Files",
                    isSelected = (indexSelect.intValue == 2),
                    onClick = {
                        navigateScanFile(TypeScan.FILE)
                        indexSelect.intValue = 2
                    }
                )
            }
        }
    }
}


@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    @DrawableRes resIcon: Int,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                1.dp,
                if (isSelected) Colors.borderSelected else Colors.borderUnSelected,
                RoundedCornerShape(12.dp)
            )
            .background(Colors.White)
            .clickable {
                onClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.width(42.dp).height(42.dp),
                painter = painterResource(resIcon),
                contentDescription = "card item"
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                text = title,
                style = Typography.bodyMedium.copy(
                    color = Colors.Black
                )
            )
            if (isSelected) {
                Image(
                    painter = painterResource(R.drawable.ic_tick_circle),
                    contentDescription = "card item"
                )
            }
        }
    }
}


// =================================== PREVIEW ====================================
@Preview
@Composable
fun CardItemPreview() {
    CardItem(
        resIcon = R.drawable.ic_recover_video,
        title = "Photos",
        isSelected = true,
        onClick = {}
    )
}

@Preview
@Composable
fun ScanTabPreview() {
    ScanTab(
        navigateScanFile = {}
    )
}