package me.rerere.rainmusic.ui.screen.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import me.rerere.rainmusic.retrofit.api.model.LyricLine
import me.rerere.rainmusic.retrofit.api.model.parse
import me.rerere.rainmusic.service.MusicService
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.local.LocalUserData
import me.rerere.rainmusic.ui.states.rememberCurrentMediaItem
import me.rerere.rainmusic.ui.states.rememberMediaSessionPlayer
import me.rerere.rainmusic.ui.states.rememberPlayProgress
import me.rerere.rainmusic.ui.states.rememberPlayState
import me.rerere.rainmusic.util.formatAsPlayerTime
import me.rerere.rainmusic.util.toast
import kotlin.math.roundToLong

@ExperimentalMaterial3Api
@Composable
fun PlayerScreen(
    playerScreenViewModel: PlayerScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val player by rememberMediaSessionPlayer(MusicService::class.java)
    val userData = LocalUserData.current
    LaunchedEffect(userData){
        playerScreenViewModel.loadLikeList(userData.id)
    }

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
    val context = LocalContext.current
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
    val userData = LocalUserData.current

    LaunchedEffect(currentMediaItem) {
        playerScreenViewModel.loadMusicDetail(currentMediaItem?.mediaId?.toLong() ?: 0L)
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.statusBarsPadding().padding(vertical = 16.dp),
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
                        text = currentMediaItem?.mediaMetadata?.title?.toString() ?: "暂未播放",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = currentMediaItem?.mediaMetadata?.artist?.toString() ?: "暂未播放",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
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
                    val percent: Float = progress?.let { (it.first * 100f / it.second) / 100f } ?: 0f

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
                        16.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    var show by remember {
                        mutableStateOf(false)
                    }
                    DropdownMenu(
                        expanded = show,
                        onDismissRequest = { show = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            show = false
                            player.repeatMode = Player.REPEAT_MODE_ONE
                        }) {
                            Text(text = "单曲循环")
                        }
                        DropdownMenuItem(onClick = {
                            show = false
                            player.repeatMode = Player.REPEAT_MODE_ALL
                        }) {
                            Text(text = "列表循环")
                        }
                    }
                    IconButton(onClick = {
                       show = true
                    }) {
                        Icon(Icons.Rounded.Repeat, null)
                    }

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
                            modifier = Modifier.size(60.dp),
                            imageVector = if (isPlaying == true) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = {
                        player.seekToNextMediaItem()
                    }) {
                        Icon(Icons.Rounded.SkipNext, null)
                    }

                    val likeList by playerScreenViewModel.likeList.collectAsState()
                    IconButton(onClick = {
                        playerScreenViewModel.like(userData.id)
                    }) {
                        Icon(
                            imageVector = if(likeList.readSafely()?.ids?.contains(currentMediaItem?.mediaId?.toLong() ?: 0) == true) {
                                Icons.Rounded.Favorite
                            } else {
                                Icons.Rounded.FavoriteBorder
                            },
                            contentDescription = null
                        )
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
                                .clip(CircleShape)
                                .fillMaxWidth(0.5f)
                                .aspectRatio(1f)
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
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val lyric by playerScreenViewModel.lyric.collectAsState()
                        val lyricLines = lyric.readSafely()?.parse()
                        val listState = rememberLazyListState()

                        var currentLyricIndex by remember {
                            mutableStateOf(0)
                        }

                        LaunchedEffect(progress) {
                            lyricLines?.let { lines ->
                                val currentLyric = (progress?.first?.div(1000) ?: 0).toInt()
                                val index = lines.indexOfLast { lyric ->
                                    lyric.time <= currentLyric
                                }
                                index.takeIf { i -> i >= 0}?.let { i ->
                                    if(listState.firstVisibleItemIndex < i) {
                                        listState.animateScrollToItem(i)
                                    }
                                    currentLyricIndex = i
                                }
                            }
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            state = listState
                        ) {
                            if (lyricLines?.isEmpty() != false) {
                                item {
                                    Text(text = "没有歌词")
                                }
                            }

                            lyric.readSafely()?.parse()?.let { lines ->
                                itemsIndexed(lines) { index, item ->
                                    LyricItem(item, currentLyricIndex == index)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 歌词Item组件
 *
 * @param lyricLine 歌词数据
 * @param currentLyric 是否为当前歌词, 需要放大高亮显示
 */
@Composable
private fun LyricItem(lyricLine: LyricLine, currentLyric: Boolean = false) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = lyricLine.lyric,
            fontWeight = if (currentLyric) FontWeight.Bold else LocalTextStyle.current.fontWeight,
            style = if (currentLyric) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium
        )
        lyricLine.translation?.let {
            Text(
                text = it,
                fontWeight = if (currentLyric) FontWeight.Bold else LocalTextStyle.current.fontWeight,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}