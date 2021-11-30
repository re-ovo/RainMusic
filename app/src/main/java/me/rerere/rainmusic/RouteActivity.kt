package me.rerere.rainmusic

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import me.rerere.rainmusic.model.UserData
import me.rerere.rainmusic.repo.UserRepo
import me.rerere.rainmusic.ui.local.LocalNavController
import me.rerere.rainmusic.ui.local.LocalUserData
import me.rerere.rainmusic.ui.screen.Screen
import me.rerere.rainmusic.ui.screen.dailysong.DailySongScreen
import me.rerere.rainmusic.ui.screen.index.IndexScreen
import me.rerere.rainmusic.ui.screen.login.LoginScreen
import me.rerere.rainmusic.ui.screen.player.PlayerScreen
import me.rerere.rainmusic.ui.screen.playlist.PlaylistScreen
import me.rerere.rainmusic.ui.screen.search.SearchScreen
import me.rerere.rainmusic.ui.screen.test.TestScreen
import me.rerere.rainmusic.ui.theme.RainMusicTheme
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.defaultEnterTransition
import me.rerere.rainmusic.util.defaultPopExitTransition
import me.rerere.rainmusic.util.toast
import okhttp3.OkHttpClient
import javax.inject.Inject

@AndroidEntryPoint
class RouteActivity : ComponentActivity() {
    @Inject
    lateinit var userRepo: UserRepo

    @Inject
    lateinit var okHttpClient: OkHttpClient

    var preparingData = true
    var userData by mutableStateOf(UserData.VISITOR)

    @ExperimentalPagerApi
    @ExperimentalMaterial3Api
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge to Edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 启动闪屏
        installSplashScreen().apply {
            // 准备完数据才结束splash screen
            setKeepVisibleCondition { preparingData }
        }

        // splash screen时加载用户数据
        init()

        setContent {
            RainMusicTheme {
                ProvideWindowInsets {
                    val navController = rememberAnimatedNavController()

                    CompositionLocalProvider(
                        // 全局提供NavController
                        LocalNavController provides navController,
                        // 全局提供用户账号信息
                        LocalUserData provides userData,
                        // Coil
                        LocalImageLoader provides ImageLoader.Builder(this)
                            .okHttpClient(okHttpClient)
                            .build()
                    ) {
                        AnimatedNavHost(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                            startDestination = "index",
                            enterTransition = defaultEnterTransition,
                            exitTransition = {
                                if (targetState.destination.route == Screen.Player.route) {
                                    fadeOut()
                                } else {
                                    slideOutHorizontally(
                                        targetOffsetX = {
                                            -it
                                        },
                                        animationSpec = tween()
                                    ) + fadeOut(
                                        animationSpec = tween()
                                    )
                                }
                            },
                            popEnterTransition = {
                                if (initialState.destination.route == Screen.Player.route) {
                                    fadeIn()
                                } else {
                                    slideInHorizontally(
                                        initialOffsetX = {
                                            -it
                                        },
                                        animationSpec = tween()
                                    )
                                }
                            },
                            popExitTransition = defaultPopExitTransition
                        ) {
                            composable(Screen.Login.route) {
                                LoginScreen()
                            }

                            composable(Screen.Index.route) {
                                IndexScreen()
                            }

                            composable(Screen.Search.route) {
                                SearchScreen()
                            }

                            composable(
                                route = "${Screen.Playlist.route}/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        type = NavType.LongType
                                    }
                                )
                            ) {
                                PlaylistScreen(id = it.arguments!!.getLong("id"))
                            }

                            composable(
                                route = Screen.Player.route,
                                enterTransition = {
                                    slideInVertically(
                                        initialOffsetY = {
                                            it
                                        },
                                        animationSpec = tween()
                                    ) + fadeIn()
                                },
                                popExitTransition = {
                                    slideOutVertically(
                                        targetOffsetY = {
                                            it
                                        },
                                        animationSpec = tween()
                                    ) + fadeOut()
                                }
                            ) {
                                PlayerScreen()
                            }

                            composable(Screen.DailySong.route){
                                DailySongScreen()
                            }

                            // 测试各种API，Compose组件的Screen
                            // 不在release版本中提供这个screen
                            if(BuildConfig.DEBUG) {
                                composable("test") {
                                    TestScreen()
                                }
                            }
                        }
                    }
                }
            }
        }

        // 禁止强制深色模式
        (window.decorView
            .findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0) as? ComposeView).let {
                it?.isForceDarkAllowed = false
        }
    }

    private fun init() {
        // 自动签到
        userRepo.dailySign().onEach {
            if(it is DataState.Success) {
                it.readSafely()?.code?.takeIf { code -> code == 200 }?.let {
                    toast("自动签到成功！")
                }
            }
        }.launchIn(lifecycleScope)

        // 检查身份信息
        combine(
            userRepo.refreshLogin(),
            userRepo.getAccountDetail()
        ) { a, b ->
            a to b
        }.onEach {
            if (it.first is DataState.Error) {
                toast("未登录!")
            }

            if (it.second is DataState.Success) {
                val data = it.second.read()
                userData = UserData(
                    id = data.account!!.id,
                    nickname = data.profile!!.nickname,
                    avatarUrl = data.profile.avatarUrl
                )
            }
        }.onCompletion {
            preparingData = false
        }.launchIn(lifecycleScope)
    }

    fun retryInit() {
        init()
    }
}