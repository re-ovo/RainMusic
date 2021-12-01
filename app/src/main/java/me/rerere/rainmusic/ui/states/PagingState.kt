package me.rerere.rainmusic.ui.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineScope

@Composable
fun <K : Any, V : Any> rememberPagingState(
    pageSize: Int,
    prefetchDistance: Int = pageSize,
    cacheScope: CoroutineScope,
    pagingSourceFactory: () -> PagingSource<K, V>
) = remember {
    Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = prefetchDistance
        ),
        pagingSourceFactory = pagingSourceFactory
    ).flow.cachedIn(cacheScope)
}.collectAsLazyPagingItems()

@Composable
fun <K : Any, V : Any> rememberPagingState(
    key1: Any?,
    pageSize: Int,
    prefetchDistance: Int = pageSize,
    cacheScope: CoroutineScope,
    pagingSourceFactory: () -> PagingSource<K, V>
) = remember(key1) {
    Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = prefetchDistance
        ),
        pagingSourceFactory = pagingSourceFactory
    ).flow.cachedIn(cacheScope)
}.collectAsLazyPagingItems()
