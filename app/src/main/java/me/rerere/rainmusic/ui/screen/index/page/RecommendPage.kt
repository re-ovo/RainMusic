package me.rerere.rainmusic.ui.screen.index.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import me.rerere.rainmusic.retrofit.weapi.model.PersonalizedPlaylist
import me.rerere.rainmusic.retrofit.weapi.model.NewSongs
import me.rerere.rainmusic.ui.component.Banner
import me.rerere.rainmusic.ui.component.shimmerPlaceholder
import me.rerere.rainmusic.ui.local.LocalNavController
import me.rerere.rainmusic.ui.screen.Screen
import me.rerere.rainmusic.ui.screen.index.IndexViewModel
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.okhttp.https

@Composable
fun IndexPage(
    indexViewModel: IndexViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            RecommendPlayLists(indexViewModel)
        }

        item {
            LargeButton()
        }

        item {
            NewSongs(indexViewModel)
        }
    }
}

@Composable
private fun RecommendPlayLists(
    indexViewModel: IndexViewModel
) {
    val personalizedPlaylist by indexViewModel.personalizedPlaylist.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "推荐歌单", style = MaterialTheme.typography.headlineSmall)
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (personalizedPlaylist) {
                    is DataState.Success -> {
                        items(personalizedPlaylist.read().result) {
                            PlaylistCard(it)
                        }
                    }
                    is DataState.Loading -> {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .shimmerPlaceholder(true)
                                    .size(100.dp)
                            )
                        }
                    }
                    is DataState.Error -> {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface)
                                    .size(100.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistCard(
    playlist: PersonalizedPlaylist.Result
) {
    val navController = LocalNavController.current
    Column(
        modifier = Modifier
            .clickable {
                Screen.Playlist.navigate(navController) {
                    "$it/${playlist.id}"
                }
            }
            .padding(8.dp)
            .width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberImagePainter(data = playlist.picUrl)
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .shimmerPlaceholder(painter)
                .size(100.dp),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = playlist.name,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 2
        )
    }
}

@Composable
fun LargeButton() {
    val navController = LocalNavController.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "为你准备的", style = MaterialTheme.typography.headlineSmall)
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .clickable {

                        }
                        .aspectRatio(2f)
                        .weight(1f),
                    tonalElevation = 12.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "私人FM",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .clickable {

                        }
                        .aspectRatio(2f)
                        .weight(1f),
                    tonalElevation = 12.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "每日推荐",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewSongs(indexViewModel: IndexViewModel) {
    val personalizedSongs by indexViewModel.personalizedSongs.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "新歌速递", style = MaterialTheme.typography.headlineSmall)
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (personalizedSongs) {
                    is DataState.Success -> {
                        items(personalizedSongs.read().result) {
                            PsSongCard(it)
                        }
                    }
                    is DataState.Loading -> {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .shimmerPlaceholder(true)
                                    .size(100.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PsSongCard(
    song: NewSongs.Result
) {
    Column(
        modifier = Modifier
            .clickable {

            }
            .padding(8.dp)
            .width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberImagePainter(data = song.picUrl)
        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .shimmerPlaceholder(painter)
                .size(100.dp),
            painter = painter,
            contentDescription = null,
        )
        Text(
            text = song.name,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
        Text(
            text = song.song.artists.joinToString(", ") { it.name },
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1
        )
    }
}