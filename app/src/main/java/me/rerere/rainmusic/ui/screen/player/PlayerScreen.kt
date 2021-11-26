package me.rerere.rainmusic.ui.screen.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.isActive
import me.rerere.rainmusic.service.MusicService
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.states.rememberCurrentMediaItem
import me.rerere.rainmusic.ui.states.rememberMediaSessionPlayer
import me.rerere.rainmusic.ui.states.rememberPlayProgress
import me.rerere.rainmusic.ui.states.rememberPlayState
import me.rerere.rainmusic.util.formatAsPlayerTime
import kotlin.math.roundToLong

@ExperimentalMaterial3Api
@Composable
fun PlayerScreen(
    playerScreenViewModel: PlayerScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val player by rememberMediaSessionPlayer(MusicService::class.java)
    when (player) {
        null -> {
            NotConnectScreen()
        }
        else -> {
            PlayerUI(player!!, playerScreenViewModel)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun NotConnectScreen() {
    Scaffold(
        topBar = {
            RainTopBar(
                title = {
                    Text(text = "播放器")
                },
                navigationIcon = {
                    PopBackIcon(Icons.Rounded.Close)
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "很抱歉，无法连接到播放器服务", modifier = Modifier)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterial3Api
@Composable
private fun PlayerUI(
    player: Player,
    playerScreenViewModel: PlayerScreenViewModel
) {
    val pagerState = rememberPagerState()
    val currentMediaItem = rememberCurrentMediaItem(player)
    val progress = rememberPlayProgress(player)
    val isPlaying = rememberPlayState(player)
    val rotator by produceState(
        initialValue = 0f,
        key1 = isPlaying
    ) {
        while (isActive && isPlaying == true) {
            value = (value + 1f) % 360f
            kotlinx.coroutines.delay(12L)
        }
    }
    val musicDetail by playerScreenViewModel.musicDetail.collectAsState()

    LaunchedEffect(currentMediaItem) {
        playerScreenViewModel.loadMusicDetail(currentMediaItem?.mediaId?.toLong() ?: 0L)
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PopBackIcon(Icons.Rounded.Close)
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = currentMediaItem?.mediaMetadata?.title.toString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = currentMediaItem?.mediaMetadata?.artist.toString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                IconButton(onClick = {

                }) {
                    Icon(Icons.Rounded.Menu, null)
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val percent: Float = remember(progress) {
                        progress?.let { (it.first * 100f / it.second) / 100f } ?: 0f
                    }

                    Text(text = progress?.first?.formatAsPlayerTime() ?: "00:00")
                    var valueChanger by remember(percent) {
                        mutableStateOf(percent)
                    }
                    Slider(
                        modifier = Modifier.weight(1f),
                        value = valueChanger,
                        onValueChange = {
                            valueChanger = it
                        },
                        onValueChangeFinished = {
                            player.seekTo(
                                (valueChanger * (progress?.second ?: 0L))
                                    .roundToLong()
                                    .coerceAtLeast(0L)
                            )
                        }
                    )
                    Text(text = progress?.second?.formatAsPlayerTime() ?: "00:00")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        32.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    IconButton(onClick = {
                        player.seekToPreviousMediaItem()
                    }) {
                        Icon(Icons.Rounded.SkipPrevious, null)
                    }

                    IconButton(
                        onClick = {
                            if (player.isPlaying) {
                                player.pause()
                            } else {
                                player.play()
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(80.dp),
                            imageVector = if (isPlaying == true) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = {
                        player.seekToNextMediaItem()
                    }) {
                        Icon(Icons.Rounded.SkipNext, null)
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) { page ->
            when (page) {
                // 封面
                0 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .rotate(rotator)
                                .fillMaxWidth(0.5f)
                                .aspectRatio(1f)
                                .clip(CircleShape)
                                .background(Color.Black),
                            painter = rememberImagePainter(
                                data = musicDetail.readSafely()?.songs?.get(0)?.al?.picUrl
                            ),
                            contentDescription = null
                        )
                    }
                }

                // 歌词
                1 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(10) {
                                Text(text = "歌词系统，还没写")
                            }
                        }
                    }
                }
            }
        }
    }
}