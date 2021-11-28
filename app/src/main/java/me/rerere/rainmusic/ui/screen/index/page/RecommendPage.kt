package me.rerere.rainmusic.ui.screen.index.page

import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Radio
import androidx.compose.material.icons.rounded.Recommend
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import coil.compose.rememberImagePainter
import me.rerere.rainmusic.retrofit.weapi.model.NewSongs
import me.rerere.rainmusic.retrofit.weapi.model.PersonalizedPlaylist
import me.rerere.rainmusic.service.MusicService
import me.rerere.rainmusic.ui.component.shimmerPlaceholder
import me.rerere.rainmusic.ui.local.LocalNavController
import me.rerere.rainmusic.ui.local.LocalUserData
import me.rerere.rainmusic.ui.screen.Screen
import me.rerere.rainmusic.ui.screen.index.IndexViewModel
import me.rerere.rainmusic.ui.states.asyncGetSessionPlayer
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.RainMusicProtocol
import me.rerere.rainmusic.util.media.buildMediaItem
import me.rerere.rainmusic.util.media.metadata
import me.rerere.rainmusic.util.setPaste
import me.rerere.rainmusic.util.toast

@Composable
fun IndexPage(
    indexViewModel: IndexViewModel
) {
    val recommendStatus by indexViewModel.personalizedPlaylist.collectAsState()
    val userData = LocalUserData.current
    LaunchedEffect(userData) {
        if (recommendStatus.notLoaded) {
            indexViewModel.refreshIndexPage()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            RecommendPlayLists(indexViewModel)
        }

        item {
            LargeButton()
        }

        item {
            HotComment(indexViewModel)
        }

        item {
            NewSongs(indexViewModel)
        }

        item {
            Toplist(indexViewModel)
        }
    }
}

@Composable
private fun HotComment(indexViewModel: IndexViewModel) {
    val hotComment by indexViewModel.hotComment.collectAsState()
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "热评", style = MaterialTheme.typography.headlineSmall)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 0.dp // 不需要给TextButton留Padding, 那样太丑
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (hotComment) {
                    is DataState.Success -> {
                        Text(
                            text = hotComment.readSafely()?.data?.content ?: "",
                            maxLines = 7,
                            modifier = Modifier.clickable {
                                context.asyncGetSessionPlayer(MusicService::class.java) {
                                    it.apply {
                                        stop()
                                        clearMediaItems()
                                        addMediaItem(
                                            buildMediaItem(hotComment.read().data.id) {
                                                metadata {
                                                    setTitle(hotComment.read().data.song)
                                                    setMediaUri(Uri.parse("$RainMusicProtocol://music?id=${hotComment.read().data.id}"))
                                                }
                                            }
                                        )
                                        prepare()
                                        play()
                                    }
                                }
                                context.toast("开始播放: ${hotComment.read().data.song}")
                            }
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
                        ) {
                            Text(
                                text = "@${hotComment.readSafely()?.data?.name ?: ""}",
                                fontSize = LocalTextStyle.current.fontSize * 0.75,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "《${hotComment.readSafely()?.data?.song}》",
                                fontWeight = FontWeight.Bold,
                                fontSize = LocalTextStyle.current.fontSize * 0.75
                            )
                        }
                    }
                    is DataState.Loading -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(text = "加载中 ~")
                        }
                    }
                    is DataState.Error -> {
                        Text(text = "加载失败: ${hotComment.javaClass.simpleName}")
                    }
                    else -> {
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
                ) {
                    TextButton(onClick = {
                        hotComment.readSafely()?.data?.content?.let {
                            context.setPaste(it)
                        }
                    }) {
                        Text(text = "复制")
                    }

                    TextButton(onClick = {
                        indexViewModel.refreshHotComment()
                    }) {
                        Text(text = "下一个")
                    }
                }
            }

        }
    }
}

@Composable
private fun RecommendPlayLists(
    indexViewModel: IndexViewModel
) {
    val personalizedPlaylist by indexViewModel.personalizedPlaylist.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "推荐歌单", style = MaterialTheme.typography.headlineSmall)
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (personalizedPlaylist) {
                    is DataState.Success -> {
                        items(personalizedPlaylist.read().result) {
                            PlaylistCard(it)
                        }
                    }
                    is DataState.Loading -> {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .shimmerPlaceholder(true)
                                    .size(100.dp)
                            )
                        }
                    }
                    is DataState.Error -> {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface)
                                    .size(100.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistCard(
    playlist: PersonalizedPlaylist.Result
) {
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
        val painter = rememberImagePainter(data = playlist.picUrl)
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

@Composable
fun LargeButton() {
    val navController = LocalNavController.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "为你准备的", style = MaterialTheme.typography.headlineSmall)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f),
                tonalElevation = 12.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(Icons.Rounded.Radio, null)
                    Text(
                        text = "私人FM",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        Screen.DailySong.navigate(navController)
                    },
                tonalElevation = 12.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(Icons.Rounded.Recommend, null)
                    Text(
                        text = "每日推荐",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@Composable
fun NewSongs(indexViewModel: IndexViewModel) {
    val personalizedSongs by indexViewModel.personalizedSongs.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "新歌速递", style = MaterialTheme.typography.headlineSmall)
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (personalizedSongs) {
                    is DataState.Success -> {
                        items(personalizedSongs.read().result) {
                            PsSongCard(it)
                        }
                    }
                    is DataState.Loading -> {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .shimmerPlaceholder(true)
                                    .size(100.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PsSongCard(
    song: NewSongs.Result
) {
    Column(
        modifier = Modifier
            .clickable {

            }
            .padding(8.dp)
            .width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberImagePainter(data = song.picUrl)
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .shimmerPlaceholder(painter)
                .size(100.dp),
            painter = painter,
            contentDescription = null,
        )
        Text(
            text = song.name,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
        Text(
            text = song.song.artists.joinToString(", ") { it.name },
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1
        )
    }
}

@Composable
private fun Toplist(indexViewModel: IndexViewModel) {
    val toplist by indexViewModel.toplist.collectAsState()
    val navController = LocalNavController.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "榜单", style = MaterialTheme.typography.headlineSmall)
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (toplist) {
                    is DataState.Success -> {
                        items(toplist.read().list) { item ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    Screen.Playlist.navigate(navController) {
                                        addPath(item.id.toString())
                                    }
                                }
                            ) {
                                val painter = rememberImagePainter(data = item.coverImgUrl)
                                Image(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .shimmerPlaceholder(painter)
                                        .size(100.dp),
                                    painter = painter,
                                    contentDescription = null,
                                )
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.labelLarge,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                    is DataState.Loading -> {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .shimmerPlaceholder(true)
                                    .size(100.dp)
                            )
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }
}