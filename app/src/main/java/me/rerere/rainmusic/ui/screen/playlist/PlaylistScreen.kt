package me.rerere.rainmusic.ui.screen.playlist

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar

@ExperimentalMaterial3Api
@Composable
fun PlaylistScreen(
    id: Int,
    playlistViewModel: PlaylistViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            RainTopBar(
                title = {
                    Text(text = "歌单详情")
                },
                navigationIcon = {
                    PopBackIcon()
                }
            )
        }
    ) {

    }
}