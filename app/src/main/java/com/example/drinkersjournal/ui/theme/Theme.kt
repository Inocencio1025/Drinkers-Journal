package com.example.drinkersjournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColorScheme(
    primary = Color.Transparent,
    onPrimary = Gold90,
    primaryContainer = Color.Cyan,
    onPrimaryContainer = Black10,
    inversePrimary = Gold40,
    secondary = Black20,
    onSecondary = Black20,
    secondaryContainer = Black40,
    onSecondaryContainer = Color.Yellow,
    tertiary = GoldTrans,
    surface = Black10,
    onSurface = Black10,
    tertiaryContainer = Black10


)
private val DarkColorPalette = darkColorScheme(
    primary = Blue,
    onPrimary = Red, //
    primaryContainer = Red, //
    onPrimaryContainer = Red, //
    inversePrimary = Red, //
    secondary = Red,
    onSecondary = Red, //
    secondaryContainer = Red, //
    onSecondaryContainer = Black90, //
    surface = Black30,
    onSurface = Black80
)

@Composable
fun DrinkersJournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}