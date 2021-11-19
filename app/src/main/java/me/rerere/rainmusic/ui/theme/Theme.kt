package me.rerere.rainmusic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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

    // 设置状态栏和底栏的颜色为透明，用于支持edge-to-edge
    val systemUiController = rememberSystemUiController()
    val darkIcon = MaterialTheme.colors.isLight
    SideEffect {
        systemUiController.setNavigationBarColor(Color.Transparent, darkIcon)
        systemUiController.setStatusBarColor(Color.Transparent, darkIcon)
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}