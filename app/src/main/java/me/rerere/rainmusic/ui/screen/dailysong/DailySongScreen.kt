package me.rerere.rainmusic.ui.screen.dailysong

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import me.rerere.rainmusic.retrofit.api.model.DailyRecommendSongs
import me.rerere.rainmusic.service.MusicService
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.component.shimmerPlaceholder
import me.rerere.rainmusic.ui.states.asyncGetSessionPlayer
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.RainMusicProtocol
import me.rerere.rainmusic.util.media.buildMediaItem
import me.rerere.rainmusic.util.media.metadata

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailySongScreen(
    dailySongViewModel: DailySongViewModel = hiltViewModel()
) {
    val dailySongs by dailySongViewModel.dailySongs.collectAsState()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            RainTopBar(
                title = {
                    Text(text = "每日推荐")
                },
                navigationIcon = {
                    PopBackIcon()
                },
                actions = {
                    IconButton(onClick = {
                        context.asyncGetSessionPlayer(MusicService::class.java) {
                            it.apply {
                                stop()
                                clearMediaItems()
                                dailySongs.readSafely()?.data?.dailySongs?.forEach { track ->
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
                                }
                                prepare()
                                play()
                            }
                        }
                    }) {
                        Icon(Icons.Rounded.PlayArrow, null)
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = rememberInsetsPaddingValues(insets = LocalWindowInsets.current.navigationBars)
        ){
            when(dailySongs){
                is DataState.Success -> {
                    dailySongs.readSafely()?.data?.dailySongs?.let {
                        itemsIndexed(it){ index, item ->
                            PlaylistMusic(index + 1, item)
                        }
                    }
                }
                is DataState.Loading -> {
                    items(5){
                        Box(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(80.dp)
                            .shimmerPlaceholder(true)
                        )
                    }
                }
                is DataState.Error -> {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(text = "加载失败")
                        }
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun PlaylistMusic(
    index: Int,
    track: DailyRecommendSongs.Data.DailySong
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