package me.rerere.rainmusic.ui.screen.index.page

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import me.rerere.rainmusic.ui.screen.index.IndexViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DiscoverPage(indexViewModel: IndexViewModel) {
    val pagerState = rememberPagerState()
    Column {
        HorizontalPager(
            count = 0,
            state = pagerState
        ) {
            PlaylistCat(indexViewModel)
        }
    }
}

@Composable
private fun PlaylistCat(indexViewModel: IndexViewModel) {

}