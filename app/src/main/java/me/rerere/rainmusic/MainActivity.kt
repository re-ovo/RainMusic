package me.rerere.rainmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.request.CachePolicy
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.rerere.rainmusic.repo.UserRepo
import me.rerere.rainmusic.ui.local.LocalNavController
import me.rerere.rainmusic.ui.local.LocalUserData
import me.rerere.rainmusic.ui.local.UserData
import me.rerere.rainmusic.ui.screen.Screen
import me.rerere.rainmusic.ui.screen.index.IndexScreen
import me.rerere.rainmusic.ui.screen.login.LoginScreen
import me.rerere.rainmusic.ui.screen.player.PlayerScreen
import me.rerere.rainmusic.ui.screen.playlist.PlaylistScreen
import me.rerere.rainmusic.ui.screen.search.SearchScreen
import me.rerere.rainmusic.ui.theme.RainMusicTheme
import me.rerere.rainmusic.util.*
import me.rerere.rainmusic.util.okhttp.CookieHelper
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

        installSplashScreen().apply {
            setKeepVisibleCondition {
                preparingData
            }
        }

        init()

        // Edge to Edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

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
                            exitTransition = defaultExitTransition,
                            popEnterTransition = defaultPopEnterTransition,
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

                            composable(Screen.Player.route){
                                PlayerScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun init() {
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

    fun retryInit(){
        init()
    }
}