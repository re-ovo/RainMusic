package me.rerere.rainmusic.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar

@Composable
fun RainMusicTopBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
) {
    TopAppBar(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        elevation = elevation,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.statusBars,
            applyBottom = false
        )
    )
}