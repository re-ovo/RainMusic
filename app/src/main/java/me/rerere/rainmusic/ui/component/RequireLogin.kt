package me.rerere.rainmusic.ui.component

import androidx.compose.runtime.Composable
import me.rerere.rainmusic.model.UserData
import me.rerere.rainmusic.ui.local.LocalUserData

/**
 * 只在登录后显示的内容
 *
 * @param content 内容
 */
@Composable
fun RequireLoginVisible(
    content: @Composable () -> Unit
) {
    val userData = LocalUserData.current
    if(!userData.isVisitor){
        content()
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