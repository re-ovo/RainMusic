package me.rerere.rainmusic.ui.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

fun Modifier.shimmerPlaceholder(
    visible: Boolean
) = composed {
    Modifier.placeholder(
        visible = visible,
        highlight = PlaceholderHighlight.shimmer()
    )
}