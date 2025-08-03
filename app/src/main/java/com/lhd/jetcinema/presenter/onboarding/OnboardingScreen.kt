package com.lhd.jetcinema.presenter.onboarding

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lhd.jetcinema.domain.model.OnBoardingModel
import com.lhd.jetcinema.presenter.theme.Colors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun OnboardingScreen(
    navigateToHome: () -> Unit
) {
    val pageCount = OnBoardingModel.onboardings.size
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )

    Box(
        modifier = Modifier
            .background(Colors.white)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints {
            val parentHeight: Dp = maxHeight
            val heightPager = parentHeight * 2f / 3f
            Column {
                HorizontalPager(
                    modifier = Modifier.height(heightPager),
                    state = pagerState,
                    pageSize = PageSize.Fill
                ) { index ->
                    OnboardingItem(
                        onBoardingModel = OnBoardingModel.onboardings[index]
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = Colors.primary,
                        contentColor = Colors.white,
                        disabledContainerColor = Colors.black,
                        disabledContentColor = Colors.black
                    ),
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage == pageCount - 1) {
                                withContext(Dispatchers.Main) {
                                    navigateToHome.invoke()
                                }
                                return@launch
                            }
                            val nextPage = (pagerState.currentPage + 1).coerceAtMost(pageCount - 1)
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Continue",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = (pagerState.currentPage != pageCount - 1)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        repeat(pageCount) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(if (isSelected) 24.dp else 6.dp)
                                    .height(6.dp)
                                    .background(
                                        color = if (isSelected) Colors.primary else Colors.black,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = (pagerState.currentPage == pageCount - 1)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                // TODO: navigate to sign in screen
                            },
                        text = "Sign In",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun OnboardingItem(
    modifier: Modifier = Modifier,
    onBoardingModel: OnBoardingModel
) {
    Column(
        modifier = modifier
            .background(Colors.transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = onBoardingModel.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Text(
            modifier = Modifier.padding(
                top = 12.dp,
                bottom = 8.dp,
                start = 12.dp,
                end = 12.dp
            ),
            text = onBoardingModel.title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.W600
            )
        )
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = onBoardingModel.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun OnboardingItemPreview() {
    OnboardingItem(
        onBoardingModel = OnBoardingModel.onboardings.first()
    )
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        navigateToHome = {}
    )
}