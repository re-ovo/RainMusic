package me.rerere.rainmusic.ui.screen.index

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import me.rerere.rainmusic.MainActivity
import me.rerere.rainmusic.R
import me.rerere.rainmusic.ui.component.*
import me.rerere.rainmusic.ui.local.LocalNavController
import me.rerere.rainmusic.ui.local.LocalUserData
import me.rerere.rainmusic.ui.screen.Screen
import me.rerere.rainmusic.ui.screen.index.page.DiscoverPage
import me.rerere.rainmusic.ui.screen.index.page.IndexPage
import me.rerere.rainmusic.ui.screen.index.page.LibraryPage
import me.rerere.rainmusic.util.DataState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterial3Api
@Composable
fun IndexScreen(
    indexViewModel: IndexViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        // modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            IndexTopBar(
                indexViewModel = indexViewModel,
                scaffoldState = scaffoldState,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavigationBar(
                pagerState = pagerState
            )
        }
    ) {
        Column {
            NetworkBanner(indexViewModel)

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                count = 3,
                state = pagerState,
            ) { page ->
                when (page) {
                    0 -> {
                        IndexPage(indexViewModel = indexViewModel)
                    }
                    1 -> {
                        DiscoverPage()
                    }
                    2 -> {
                        LibraryPage()
                    }
                }
            }
        }
    }
}

@Composable
private fun NetworkBanner(
    indexViewModel: IndexViewModel
) {
    val context = LocalContext.current
    val data by indexViewModel.personalizedSongs.collectAsState()
    AnimatedVisibility(
        visible = data is DataState.Error
    ) {
        NetworkIssueBanner {
            (context as MainActivity).retryInit()
            indexViewModel.refreshIndexPage()
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
private fun IndexTopBar(
    indexViewModel: IndexViewModel,
    scaffoldState: ScaffoldState,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
    // val accountDetail by indexViewModel.accountDetail.collectAsState()
    val accountData = LocalUserData.current
    RainTopBar(
        navigationIcon = {
            val avatarPainter = rememberImagePainter(
                data = if (!accountData.isVisitor)
                    accountData.avatarUrl
                else
                    R.drawable.netease_music
            )
            IconButton(onClick = {
                // TODO
                navController.navigate("login")
            }) {
                Icon(
                    modifier = Modifier
                        .clip(CircleShape)
                        .shimmerPlaceholder(avatarPainter.state is ImagePainter.State.Loading),
                    painter = avatarPainter,
                    contentDescription = "avatar",
                    tint = Color.Unspecified
                )
            }
        },
        title = {
            if (!accountData.isVisitor) {
                Text(text = accountData.nickname)
            } else {
                Text(text = stringResource(R.string.app_name))
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.Search.route)
            }) {
                Icon(Icons.Rounded.Search, "Search")
            }
        },
        appBarStyle = AppBarStyle.Small,
        scrollBehavior = scrollBehavior
    )
}

@ExperimentalPagerApi
@Composable
private fun BottomNavigationBar(
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()
    RainBottomNavigation {
        NavigationBarItem(
            selected = pagerState.currentPage == 0,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        page = 0
                    )
                }
            },
            icon = {
                Icon(Icons.Rounded.Recommend, null)
            },
            label = {
                Text(text = "推荐")
            }
        )

        NavigationBarItem(
            selected = pagerState.currentPage == 1,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        page = 1
                    )
                }
            },
            icon = {
                Icon(Icons.Rounded.FeaturedPlayList, null)
            },
            label = {
                Text(text = "发现")
            }
        )

        NavigationBarItem(
            selected = pagerState.currentPage == 2,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(
                        page = 2
                    )
                }
            },
            icon = {
                Icon(Icons.Rounded.Headphones, null)
            },
            label = {
                Text(text = "音乐库")
            }
        )
    }
}

@Composable
private fun DrawerContent() {
    Text(text = "TODO...")
}

// 推荐歌单
@Composable
private fun RecommendPlaylist() {
    Column {
        Text(text = "推荐歌单")

    }
}