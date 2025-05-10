package com.lhd.jetcinema.screen.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lhd.jetcinema.R

val Mulish = FontFamily(
    Font(R.font.mulish_regular, FontWeight.Normal),
    Font(R.font.mulish_light,FontWeight.Light),
    Font(R.font.mulish_semibold, FontWeight.SemiBold),
    Font(R.font.mulish_bold, FontWeight.ExtraBold)
)

// Set of Material typography styles to start with
val JetCinemaTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Mulish,
        fontSize = 57.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = Mulish,
        fontSize = 45.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Mulish,
        fontSize = 36.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 44.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Mulish,
        fontSize = 32.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Mulish,
        fontSize = 28.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Mulish,
        fontSize = 24.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Mulish,
        fontSize = 22.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Mulish,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Mulish,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Mulish,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Mulish,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Mulish,
        fontSize = 11.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Mulish,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Mulish,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Mulish,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
)
