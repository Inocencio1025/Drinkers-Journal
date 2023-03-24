package com.example.drinkersjournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColorScheme(
    primary = Gold80,
    onPrimary = Gold20,
    primaryContainer = Gold30,
    onPrimaryContainer = Gold90,
    inversePrimary = Gold40,
    secondary = Black80,
    onSecondary = Black20,
    secondaryContainer = Black30,
    onSecondaryContainer = Black90,
)

private val LightColorPalette = lightColorScheme(
    primary = Gold80,
    onPrimary = Gold20,
    primaryContainer = Gold30,
    onPrimaryContainer = Gold90,
    inversePrimary = Gold40,
    secondary = Black80,
    onSecondary = Black20,
    secondaryContainer = Black30,
    onSecondaryContainer = Black90,

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