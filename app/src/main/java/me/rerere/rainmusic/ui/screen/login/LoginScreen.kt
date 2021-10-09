package me.rerere.rainmusic.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import me.rerere.rainmusic.R
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
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .navigationBarsWithImePadding()
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Body(
                loginViewModel
            )
        }
    }
}

@Composable
private fun Body(
    loginViewModel: LoginViewModel
) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.netease_music),
            contentDescription = null,
            modifier = Modifier
                .padding(32.dp)
                //.clip(CircleShape)
                .size(100.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = {
                Text(text = "手机号")
            },
            leadingIcon = {
                Text(
                    text = "+86",
                    color = MaterialTheme.colors.primary
                )
            }
        )

        var passwordVisible by remember {
            mutableStateOf(false)
        }
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "密码")
            },
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(
                        imageVector = if(passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

            }
        ) {
            Text(text = "登录")
        }

        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "本APP仅会和网易官方API通信，不会泄露你的密码，请放心使用"
            )
        }
    }
}