package me.rerere.rainmusic.ui.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
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

@OptIn(ExperimentalCoilApi::class)
fun Modifier.shimmerPlaceholder(
    imagePainter: ImagePainter
) = composed {
    Modifier.placeholder(
        visible = imagePainter.state is ImagePainter.State.Loading,
        highlight = PlaceholderHighlight.shimmer()
    )
}