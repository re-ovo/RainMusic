package me.rerere.rainmusic.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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

    // 设置状态栏和底栏的颜色为透明，用于支持edge-to-edge
    val systemUiController = rememberSystemUiController()
    val darkIcon = !darkTheme
    SideEffect {
        systemUiController.setNavigationBarColor(Color.Transparent, darkIcon)
        systemUiController.setStatusBarColor(Color.Transparent, darkIcon)
    }

    // 某些库任然在使用MD2，此时将MD3 Color转换到MD2的Color实现对某些组件
    // 库的兼容性~
    androidx.compose.material.MaterialTheme(
        colors = colorScheme.toMd2Colors(!darkTheme)
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}