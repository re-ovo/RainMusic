package me.rerere.rainmusic.ui.screen.search

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import me.rerere.rainmusic.ui.component.PopBackIcon
import me.rerere.rainmusic.ui.component.RainTopBar

@ExperimentalMaterial3Api
@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            RainTopBar(
                navigationIcon = {
                    PopBackIcon()
                },
                title = {
                    Text(text = "搜索")
                }
            )
        }
    ) {

    }
}