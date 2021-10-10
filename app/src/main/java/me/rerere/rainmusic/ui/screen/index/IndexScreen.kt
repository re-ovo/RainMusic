package me.rerere.rainmusic.ui.screen.index

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.insets.ui.Scaffold
import me.rerere.rainmusic.R
import me.rerere.rainmusic.ui.component.RainMusicTopBar

@Composable
fun IndexScreen(

) {
    Scaffold(
        topBar = {
            RainMusicTopBar(
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {

                    }
                },
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "search"
                        )
                    }
                }
            )
        }
    ) {

    }
}