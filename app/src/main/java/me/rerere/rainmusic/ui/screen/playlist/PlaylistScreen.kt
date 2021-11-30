package me.rerere.rainmusic.ui.screen.playlist

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.launch
import me.rerere.rainmusic.retrofit.api.model.PlaylistDetail
import me.rerere.rainmusic.service.MusicService
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.component.shimmerPlaceholder
import me.rerere.rainmusic.ui.states.asyncGetSessionPlayer
import me.rerere.rainmusic.util.RainMusicProtocol
import me.rerere.rainmusic.util.media.buildMediaItem
import me.rerere.rainmusic.util.media.metadata
import me.rerere.rainmusic.util.toast

@ExperimentalMaterial3Api
@Composable
fun PlaylistScreen(
    id: Long,
    playlistViewModel: PlaylistViewModel = hiltViewModel()
) {
    LaunchedEffect(id) {
        playlistViewModel.loadPlaylist(id)
    }

    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            PlaylistTopBar(playlistViewModel, scrollBehavior)
        },
        floatingActionButton = {
            if (lazyListState.firstVisibleItemIndex > 10) {
                FloatingActionButton(
                    modifier = Modifier.navigationBarsPadding(),
                    onClick = {
                        scope.launch {
                            lazyListState.scrollToItem(0)
                        }
                    }
                ) {
                    Icon(Icons.Rounded.ArrowUpward, null)
                }
            }
        }
    ) {
        val playlistDetail by playlistViewModel.playlistDetail.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = lazyListState
        ) {
            item {
                PlaylistInfo(playlistViewModel)
            }

            item {
                PlaylistAction(playlistViewModel)
            }

            playlistDetail.readSafely()?.let {
                itemsIndexed(it.playlist.tracks) { index, item ->
                    PlaylistMusic(
                        index = index + 1,
                        track = item
                    )
                }
            }
        }
    }
}

@Composable
private fun PlaylistTopBar(
    playlistViewModel: PlaylistViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val playlistDetail by playlistViewModel.playlistDetail.collectAsState()
    RainTopBar(
        navigationIcon = {
            PopBackIcon()
        },
        title = {
            Text(text = "歌单详情")
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun PlaylistInfo(playlistViewModel: PlaylistViewModel) {
    val playlistDetail by playlistViewModel.playlistDetail.collectAsState()
    var showPlaylistDetailDialog by remember {
        mutableStateOf(false)
    }
    if (showPlaylistDetailDialog) {
        AlertDialog(
            onDismissRequest = {
                showPlaylistDetailDialog = false
            },
            title = {
                Text(text = playlistDetail.readSafely()?.playlist?.name ?: "歌单信息")
            },
            text = {
                SelectionContainer {
                    Text(text = playlistDetail.readSafely()?.playlist?.description ?: "加载中")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showPlaylistDetailDialog = false
                }) {
                    Text(text = "关闭")
                }
            }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter =
            rememberImagePainter(data = playlistDetail.readSafely()?.playlist?.coverImgUrl)
        Image(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerPlaceholder(painter),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.clickable {
                showPlaylistDetailDialog = true
            },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = playlistDetail.readSafely()?.playlist?.name ?: "加载中",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2
            )
            Text(
                text = playlistDetail.readSafely()?.playlist?.creator?.nickname ?: "",
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = playlistDetail.readSafely()?.playlist?.description ?: "歌单描述",
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun PlaylistAction(
    playlistViewModel: PlaylistViewModel
) {
    val playlistDetail by playlistViewModel.playlistDetail.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = {
            context.asyncGetSessionPlayer(MusicService::class.java) {
                it.apply {
                    stop()
                    clearMediaItems()
                    playlistDetail.readSafely()?.playlist?.tracks?.forEach { track ->
                        addMediaItem(
                            buildMediaItem(track.id.toString()){
                                metadata {
                                    setTitle(track.name)
                                    setArtist(track.ar.joinToString(", ") { ar -> ar.name })
                                    setMediaUri(Uri.parse("$RainMusicProtocol://music?id=${track.id}"))
                                    setArtworkUri(Uri.parse(track.al.picUrl))
                                }
                            }
                        )
                    }
                    prepare()
                    play()
                    context.toast("开始播放该歌单")
                }
            }
        }) {
            Text(text = "播放")
        }

        IconButton(onClick = {
            playlistViewModel.subscribe(scope)
        }) {
            val imageVector = if (playlistDetail.readSafely()?.playlist?.subscribed == true) {
                Icons.Rounded.Favorite
            } else {
                Icons.Rounded.FavoriteBorder
            }
            Icon(imageVector, null)
        }

        Text(
            modifier = Modifier.weight(1f),
            text = "共 ${playlistDetail.readSafely()?.playlist?.trackCount} 首歌",
            maxLines = 1,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun PlaylistMusic(
    index: Int,
    track: PlaylistDetail.Playlist.Track
) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        tonalElevation = if (index % 2 == 0) 8.dp else 16.dp,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = index.toString())

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = track.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    Text(
                        text = track.ar.joinToString(separator = "/") { it.name } + if (track.al.name.isNotBlank()) " - ${track.al.name}" else "",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            IconButton(onClick = {
                context.asyncGetSessionPlayer(MusicService::class.java) {
                    it.apply {
                        stop()
                        clearMediaItems()
                        addMediaItem(
                            buildMediaItem(track.id.toString()) {
                                metadata {
                                    setTitle(track.name)
                                    setArtist(track.ar.joinToString(", ") { ar -> ar.name })
                                    setMediaUri(Uri.parse("$RainMusicProtocol://music?id=${track.id}"))
                                    setArtworkUri(Uri.parse(track.al.picUrl))
                                }
                            }
                        )
                        prepare()
                        play()
                    }
                }
            }) {
                Icon(Icons.Rounded.PlayArrow, null)
            }
        }
    }
}