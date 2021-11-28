package me.rerere.rainmusic.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.rerere.rainmusic.model.UserData
import me.rerere.rainmusic.ui.local.LocalUserData

/**
 * 只在登录后显示的内容
 *
 * @param content 内容
 */
@Composable
fun RequireLoginVisible(
    notLoginUI: @Composable () -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = "此功能需要登录")
        }
    },
    content: @Composable () -> Unit
) {
    val userData = LocalUserData.current
    if(!userData.isVisitor){
        content()
    } else {
        notLoginUI()
    }
}

/**
 * 根据是否登录执行不同的动作，通常用于 clickable() 中
 *
 * @param notLoginAction 未登录时执行的操作
 * @param loginAction 已登录后执行的操作
 */
inline fun UserData.handle(
    notLoginAction: () -> Unit,
    loginAction: () -> Unit
) {
    if(isVisitor){
       notLoginAction()
    } else {
        loginAction()
    }
}