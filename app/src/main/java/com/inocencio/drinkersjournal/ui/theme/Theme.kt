package com.inocencio.drinkersjournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColorScheme(
    primary = Color.Transparent,
    onPrimary = Gold90,
    primaryContainer = Black40,
    onPrimaryContainer = Black10,
    inversePrimary = Gold90,
    secondary = Black20,
    onSecondary = Black20,
    secondaryContainer = Black20,
    onSecondaryContainer = Color.Yellow,
    tertiary = GoldTrans,
    surface = Black10,
    onSurface = Color.White,
    tertiaryContainer = Gold20,
    onTertiary = Gold40,
    onSurfaceVariant = Black80
)

private val DarkColorPalette = darkColorScheme(
    primary = Color.Transparent,
    onPrimary = Gold90,
    primaryContainer = Black40,
    onPrimaryContainer = Black10,
    inversePrimary = Gold40,
    secondary = Black20,
    onSecondary = Black20,
    secondaryContainer = Black20,
    onSecondaryContainer = Color.Yellow,
    tertiary = GoldTrans,
    surface = Black10,
    onSurface = Black10,
    tertiaryContainer = Black10,
    onSurfaceVariant = Black80
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