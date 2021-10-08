package me.rerere.rainmusic.ui.screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import me.rerere.rainmusic.ui.component.RainMusicTopBar

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            RainMusicTopBar(
                title = {
                    Text(text = "登录")
                }
            )
        }
    ){
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ){
            Body(
                loginViewModel
            )
        }
    }
}

@Composable
private fun Body(
    loginViewModel: LoginViewModel
){

}