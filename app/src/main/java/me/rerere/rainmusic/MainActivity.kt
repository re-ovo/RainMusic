package me.rerere.rainmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import me.rerere.rainmusic.ui.theme.RainMusicTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge to Edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            RainMusicTheme {
                ProvideWindowInsets {
                    val systemUiController = rememberSystemUiController()
                    val darkIcon = MaterialTheme.colors.isLight
                    SideEffect {
                        systemUiController.setNavigationBarColor(Color.Transparent, darkIcon)
                        systemUiController.setStatusBarColor(Color.Transparent, darkIcon)
                    }

                    val navController = rememberAnimatedNavController()
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = "index"
                    ){
                        composable("index"){
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        title = {
                                            Text(stringResource(R.string.app_name))
                                        },
                                        contentPadding = rememberInsetsPaddingValues(
                                            insets = LocalWindowInsets.current.statusBars,
                                            applyBottom = false
                                        )
                                    )
                                }
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}