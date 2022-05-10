package me.rerere.rainmusic.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import me.rerere.rainmusic.util.findActivity

// 自定义colorScheme
val LightColorScheme = lightColorScheme()
val DarkColorScheme = darkColorScheme()

@Composable
fun RainMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Android 12, 动态壁纸取色
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    ApplyBarColor(darkTheme)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun ApplyBarColor(darkTheme: Boolean) {
    val view = LocalView.current
    val activity = LocalContext.current as Activity
    SideEffect {
        view.context.findActivity().window.apply {
            statusBarColor = Color.Transparent.toArgb()
            navigationBarColor = Color.Transparent.toArgb()
        }
        WindowCompat.getInsetsController(activity.window, view).apply {
            isAppearanceLightNavigationBars = !darkTheme
            isAppearanceLightStatusBars = !darkTheme
        }
    }
}