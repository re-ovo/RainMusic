package me.rerere.rainmusic.ui.screen.player

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaStyleNotificationHelper
import me.rerere.rainmusic.service.MusicService
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.component.rememberMediaSessionPlayer
import me.rerere.rainmusic.util.toast

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

        if(player == null){
            Text(text = "加载中")
        } else {
            Column {
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
                    player?.addMediaItem(MediaItem.fromUri(""))
                }) {
                    Text(text = "添加item")
                }
            }
        }
    }
}