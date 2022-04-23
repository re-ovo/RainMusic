package me.rerere.rainmusic.ui.states

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    itemContent: @Composable (value: T?) -> Unit
) {
    items(
        count = items.itemCount
    ) { index ->
        val item = items[index]
        itemContent(item)
    }
}
