package me.rerere.rainmusic.ui.screen.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import me.rerere.rainmusic.service.MusicService
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.states.rememberMediaSessionPlayer
import kotlin.random.Random

@ExperimentalMaterial3Api
@Composable
fun PlayerScreen(
    playerScreenViewModel: PlayerScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            RainTopBar(
                title = {
                    Text(text = "播放器")
                },
                navigationIcon = {
                    PopBackIcon()
                }
            )
        }
    ) {
        val context = LocalContext.current
        val player by rememberMediaSessionPlayer(MusicService::class.java)

        if (player == null) {
            Text(text = "加载中")
        } else {
            Column(
                Modifier.padding(16.dp)
            ) {
                Text(text = "加载完成")

                Button(onClick = {
                    player?.prepare()
                    player?.play()
                }) {
                    Text(text = "播放")
                }

                Button(onClick = {
                    player?.pause()
                }) {
                    Text(text = "暂停")
                }

                Button(onClick = {
                    player?.addMediaItem(
                        MediaItem.Builder()
                            .setMediaId("1484837259")
                            .setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setTitle("xswl")
                                    .setArtist("nmnd")
                                    .setMediaUri(Uri.parse("rainmusic://music?id=1484837259"))
                                    .setArtworkUri(Uri.parse("https://p3.music.126.net/RVitYg0n99vM4jdvjI2Low==/5841705278425405.jpg"))
                                    .build()
                            )
                            .build()
                    )
                }) {
                    Text(text = "添加item")
                }
            }
        }
    }
}