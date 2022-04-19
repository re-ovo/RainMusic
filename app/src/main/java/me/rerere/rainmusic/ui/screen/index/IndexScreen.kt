package me.rerere.rainmusic.ui.screen.index

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import me.rerere.rainmusic.BuildConfig
import me.rerere.rainmusic.R
import me.rerere.rainmusic.RouteActivity
import me.rerere.rainmusic.ui.component.*
import me.rerere.rainmusic.ui.local.LocalNavController
import me.rerere.rainmusic.ui.local.LocalUserData
import me.rerere.rainmusic.ui.screen.Screen
import me.rerere.rainmusic.ui.screen.index.page.DiscoverPage
import me.rerere.rainmusic.ui.screen.index.page.IndexPage
import me.rerere.rainmusic.ui.screen.index.page.LibraryPage
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.okhttp.CookieHelper

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterial3Api
@Composable
fun IndexScreen(
    indexViewModel: IndexViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavigationDrawer(
        drawerContent = {
            DrawerContent(indexViewModel)
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                IndexTopBar(
                    indexViewModel = indexViewModel,
                    scrollBehavior = scrollBehavior,
                    drawerState = drawerState
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    pagerState = pagerState
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    Screen.Player.navigate(navController)
                }) {
                    Icon(Icons.Rounded.QueueMusic, null)
                }
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
                            IndexPage(indexViewModel)
                        }
                        1 -> {
                            DiscoverPage(indexViewModel)
                        }
                        2 -> {
                            RequireLoginVisible {
                                LibraryPage(indexViewModel)
                            }
                        }
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
            (context as RouteActivity).retryInit()
            indexViewModel.refreshIndexPage()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
private fun IndexTopBar(
    indexViewModel: IndexViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState
) {
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
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
                scope.launch {
                    drawerState.open()
                }
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
            var showDebugButtons by remember {
                mutableStateOf(false)
            }
            if (BuildConfig.DEBUG && showDebugButtons) {
                TextButton(onClick = {
                    CookieHelper.logout()
                }) {
                    Text(text = "注销登录")
                }
                TextButton(onClick = {
                    Screen.Test.navigate(navController)
                }) {
                    Text(text = "测试页面")
                }
            }

            Icon(
                modifier = Modifier
                    .combinedClickable(
                        onClick = {
                            navController.navigate(Screen.Search.route)
                        },
                        onLongClick = {
                            showDebugButtons = !showDebugButtons
                        }
                    )
                    .padding(8.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search"
            )
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
private fun DrawerContent(indexViewModel: IndexViewModel) {
    val userData = LocalUserData.current
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // TODO: 完善drawer
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = userData.avatarUrl),
                contentDescription = null,
                modifier = Modifier.clip(CircleShape)
            )
        }
    }
}