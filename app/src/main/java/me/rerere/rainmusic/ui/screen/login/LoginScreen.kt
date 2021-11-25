package me.rerere.rainmusic.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.message
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title
import dev.burnoo.compose.rememberpreference.rememberStringPreference
import me.rerere.rainmusic.RouteActivity
import me.rerere.rainmusic.R
import me.rerere.rainmusic.ui.component.RainTopBar
import me.rerere.rainmusic.ui.local.LocalNavController
import me.rerere.rainmusic.util.toast

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            RainTopBar(
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
    val context = LocalContext.current
    val navController = LocalNavController.current
    val loginState by loginViewModel.loginState.collectAsState()
    val loginDialog = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = loginDialog,
        buttons = {
            button("关闭") {
                loginDialog.hide()
            }
        }
    ) {
        title("登录")
        when (loginState) {
            1 -> {
                message("登录中, 请稍等...")
            }
            -1 -> {
                message("登录时发生错误，请检查你的网络连接")
            }
            2 -> {
                message("密码错误!")
            }
            3 -> {
                message("没有此账号!")
            }
            1000 -> {
                message("登录成功")
            }
            else -> {
                message("未知错误: $loginState")
            }
        }
    }

    LaunchedEffect(loginState) {
        loginDialog.showing = loginState != 0
        if (loginState == 1000) {
            // 登录成功
            context.toast("登录成功")
            navController.navigate("index") {
                popUpTo("login") {
                    inclusive = true
                }
            }
            (context as RouteActivity).retryInit()
        }
    }

    var username by rememberStringPreference(
        keyName = "login.phone",
        defaultValue = "",
        initialValue = ""
    )
    var password by rememberStringPreference(
        keyName = "login.password",
        defaultValue = "",
        initialValue = ""
    )
    Column(
        modifier = Modifier.width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.netease_music),
            contentDescription = null,
            modifier = Modifier
                .padding(32.dp)
                .size(100.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it.let {
                    if (it.length > 11) {
                        it.substring(0..10)
                    } else {
                        it
                    }
                }
            },
            singleLine = true,
            label = {
                Text(text = "手机号")
            },
            leadingIcon = {
                Text(
                    text = "+86",
                    color = MaterialTheme.colorScheme.primary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                loginViewModel.loginCellPhone(
                    phone = username,
                    password = password
                )
            }
        ) {
            Text(text = "登录")
        }

        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "本APP不提供 注册/开通黑胶/修改个人信息 等功能，如果需要，请自行前往网易云官方APP操作！"
            )
        }
    }
}