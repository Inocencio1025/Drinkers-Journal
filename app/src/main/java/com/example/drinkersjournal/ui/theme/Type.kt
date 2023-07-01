package com.example.drinkersjournal.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.drinkersjournal.R

// font fams
val drinkNameFont = FontFamily(
    Font(R.font.lobster_regular)
)
val drinkRatingTextFont = FontFamily(
    Font(R.font.bacasime_antique_regular)
)

// Material 3
val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    titleMedium = TextStyle(
        fontFamily = drinkNameFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

