package me.rerere.rainmusic.ui.screen.index.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DashboardCustomize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import me.rerere.rainmusic.data.model.Playlists
import me.rerere.rainmusic.data.paging.TopPlaylistPagingSource
import me.rerere.rainmusic.data.retrofit.weapi.model.PlaylistCategory
import me.rerere.rainmusic.ui.component.shimmerPlaceholder
import me.rerere.rainmusic.ui.local.LocalNavController
import me.rerere.rainmusic.ui.screen.Screen
import me.rerere.rainmusic.ui.screen.index.IndexViewModel
import me.rerere.rainmusic.ui.states.items
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.sharedPreferencesOf
import me.rerere.rainmusic.util.toast

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun DiscoverPage(indexViewModel: IndexViewModel) {
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val categoryAll by indexViewModel.categoryAll.collectAsState()
    val categorySelect by indexViewModel.categorySelected.collectAsState()
    var editing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (categoryAll.notLoaded) {
            indexViewModel.refreshExplorePage()
        }
    }

    val category = listOf("全部", "官方", "精品") + categorySelect

    if (editing) {
        CategoryEditor(categoryAll, categorySelect) {
            scope.launch {
                pagerState.scrollToPage(0)
            }
            sharedPreferencesOf("playlist_category").edit {
                putString("data", it.distinct().joinToString(","))
            }
            indexViewModel.refreshSelectedCategory()
            context.toast("保存成功")
            editing = false
        }
    } else {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScrollableTabRow(
                    modifier = Modifier.weight(1f),
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                    edgePadding = 0.dp
                ) {
                    category.forEachIndexed { index, sub ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        ) {
                            Text(text = sub, modifier = Modifier.padding(4.dp))
                        }
                    }
                }

                IconButton(onClick = { editing = true }) {
                    Icon(Icons.Rounded.DashboardCustomize, null)
                }
            }
            HorizontalPager(
                count = category.size,
                state = pagerState
            ) {
                TopPlaylist(indexViewModel, category[it])
            }
        }
    }
}

@Composable
private fun CategoryEditor(
    categoryAll: DataState<PlaylistCategory>,
    selectedCategory: List<String>,
    onSave: (List<String>) -> Unit
) {
    var category by remember(selectedCategory) {
        mutableStateOf(selectedCategory)
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "自定义歌单类型",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineSmall
            )
            Button(
                onClick = {
                    // 保存前重排序一遍
                    val list =
                        categoryAll.read().sub.filter { category.contains(it.name) }.map { it.name }
                            .toList()
                    onSave(list)
                }
            ) {
                Text(text = "保存")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            categoryAll.readSafely()?.categories?.entries?.forEach { (k, v) ->
                item {
                    Text(text = v)
                }

                item {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisSpacing = 8.dp
                    ) {
                        categoryAll.read().sub.filter { it.category == k.toInt() }.forEach { sub ->
                            if (category.contains(sub.name)) {
                                OutlinedButton(onClick = {
                                    category = ArrayList(
                                        category.toMutableList().apply { remove(sub.name) })
                                }) {
                                    Text(text = sub.name)
                                }
                            } else {
                                TextButton(onClick = {
                                    category =
                                        ArrayList(category.toMutableList().apply { add(sub.name) })
                                }) {
                                    Text(text = sub.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun TopPlaylist(
    indexViewModel: IndexViewModel,
    category: String
) {
    if (category == "精品") {
        val highQualityPlaylist by indexViewModel.highQualityPlaylist.collectAsState()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(110.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (highQualityPlaylist is DataState.Success) {
                items(highQualityPlaylist.read().playlists) { playlist ->
                    PlaylistItem(playlist)
                }
            }
        }
        return
    }

    val items = (indexViewModel.playlistCatPager[category] ?: run {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 3
            ),
            pagingSourceFactory = {
                TopPlaylistPagingSource(
                    category = category,
                    musicRepo = indexViewModel.musicRepo
                )
            }
        ).flow.cachedIn(indexViewModel.viewModelScope).also {
            indexViewModel.playlistCatPager[category] = it
        }
    }).collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(110.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { playlist ->
            PlaylistItem(playlist!!)
        }
    }
}

@Composable
private fun PlaylistItem(playlist: Playlists) {
    val navController = LocalNavController.current
    Column(
        modifier = Modifier
            .clickable {
                Screen.Playlist.navigate(navController) {
                    addPath(playlist.id.toString())
                }
            }
            .padding(8.dp)
            .width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberImagePainter(data = playlist.coverImgUrl)
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .shimmerPlaceholder(painter)
                .size(100.dp),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = playlist.name,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 2
        )
    }
}