package com.example.drinkersjournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Gold40,
    onPrimary = Gold80,
    primaryContainer = Black10,
    onPrimaryContainer = Gold40,
    inversePrimary = Gold40,
    //secondary = Black80,
    //onSecondary = Black20,
    //secondaryContainer = Black30,
    //onSecondaryContainer = Black90,
    surface = Gold50,
    onSurface = Gold80,
    outline = Black10
)

private val LightColorPalette = lightColorScheme(
    primary = Gold50,
    onPrimary = Gold80,
    primaryContainer = Gold80,
    onPrimaryContainer = Gold40,
    inversePrimary = Gold40,
    secondary = Gold80,
    onSecondary = Gold80,
    secondaryContainer = Black10,
    onSecondaryContainer = Gold60,
    surface = Black10,
    onSurface = Black10,
    outline = Gold80,
    inverseSurface = Gold80,
    inverseOnSurface = Gold80,


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