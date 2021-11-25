package me.rerere.rainmusic.ui.screen.player

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
                    player?.addMediaItem(MediaItem.Builder()
                        .setMediaId("232rr423-${Random.nextInt()}")
                        .setMediaMetadata(
                            MediaMetadata.Builder()
                                .setTitle("xswl")
                                .setArtist("nmnd")
                                .setMediaUri(Uri.parse("http://m801.music.126.net/20211125114801/cbba3dad98852f95d927e9a55c78737e/jdymusic/obj/w5zDlMODwrDDiGjCn8Ky/1609691996/d018/b670/39d4/025614517010ad15b0317eb583355fff.mp3"))
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