package com.lhd.jetcinema.domain.model

import com.lhd.jetcinema.R

data class OnBoardingModel(
    val imageRes: Int,
    val title: String,
    val description: String
) {
    companion object {
        val onboardings = listOf<OnBoardingModel>(
            OnBoardingModel(
                title = "Watching can be from anywhere",
                description = "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt sed do eiusmod tempor incididunt",
                imageRes = R.drawable.img_onboarding1
            ),
            OnBoardingModel(
                title = "Complete list of movies",
                description = "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt sed do eiusmod tempor incididunt",
                imageRes = R.drawable.img_onboarding2
            ),
            OnBoardingModel(
                title = "Spent Time with Loved Ones!",
                description = "Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incididunt sed do eiusmod tempor incididunt",
                imageRes = R.drawable.img_onboarding3
            )
        )
    }
}
