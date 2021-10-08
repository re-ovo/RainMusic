package me.rerere.rainmusic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = PrimaryBlue,
    primaryVariant = PrimaryBlue,
    secondary = PrimaryBlue,
    secondaryVariant = PrimaryBlue
)

private val LightColorPalette = lightColors(
    primary = PrimaryBlue,
    primaryVariant = PrimaryBlue,
    secondary = PrimaryBlue,
    secondaryVariant = PrimaryBlue
)

@Composable
fun RainMusicTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}