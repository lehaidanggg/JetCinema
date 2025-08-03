package com.lhd.jetcinema.presenter.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lhd.jetcinema.R

val Mono = FontFamily(
    Font(R.font.mono_light, FontWeight.Light),
    Font(R.font.mono_medium, FontWeight.Medium),
    Font(R.font.mono_regular, FontWeight.Normal),
)

// Set of Material typography styles to start with
val JetCinemaTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Mono,
        fontSize = 57.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        color = Colors.black
    ),
    displayMedium = TextStyle(
        fontFamily = Mono,
        fontSize = 45.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 52.sp,
        color = Colors.black
    ),
    displaySmall = TextStyle(
        fontFamily = Mono,
        fontSize = 36.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 44.sp,
        color = Colors.black
    ),
    headlineLarge = TextStyle(
        fontFamily = Mono,
        fontSize = 32.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 40.sp,
        color = Colors.black
    ),
    headlineMedium = TextStyle(
        fontFamily = Mono,
        fontSize = 28.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 36.sp,
        color = Colors.black
    ),
    headlineSmall = TextStyle(
        fontFamily = Mono,
        fontSize = 24.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 32.sp,
        color = Colors.black
    ),
    titleLarge = TextStyle(
        fontFamily = Mono,
        fontSize = 22.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 28.sp,
        color = Colors.black
    ),
    titleMedium = TextStyle(
        fontFamily = Mono,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = Colors.black
    ),
    titleSmall = TextStyle(
        fontFamily = Mono,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = Colors.black
    ),
    labelLarge = TextStyle(
        fontFamily = Mono,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = Colors.black
    ),
    labelMedium = TextStyle(
        fontFamily = Mono,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = Colors.black
    ),
    labelSmall = TextStyle(
        fontFamily = Mono,
        fontSize = 11.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = Colors.black
    ),
    bodyLarge = TextStyle(
        fontFamily = Mono,
        fontSize = 16.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Colors.black
    ),
    bodyMedium = TextStyle(
        fontFamily = Mono,
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = Colors.black
    ),
    bodySmall = TextStyle(
        fontFamily = Mono,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = Colors.black
    ),
)
