package me.rerere.rainmusic.ui.component

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NetworkCheck
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * 一个Material Design Banner的简易实现
 * https://material.io/components/banners#usage
 *
 * @param icon Banner图标，可不写
 * @param text Banner文本区域
 * @param actions Banner按钮区域
 */
@Composable
fun Banner(
    icon: @Composable (() -> Unit)? = null,
    text: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp ,
                bottom = 0.dp // 不需要给TextButton留Padding, 那样太丑
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if(icon != null){
                    icon()
                }
                text()
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
            ) {
                actions()
            }
        }
    }
}

/**
 * 一个显示网络连接错误的Banner实现
 *
 * @param retryAction 重试操作
 */
@Composable
internal fun NetworkIssueBanner(
    retryAction: () -> Unit
) {
    val context = LocalContext.current
    Banner(
        icon = {
            Icon(Icons.Rounded.NetworkCheck, null)
        },
        text = {
            Text(text = "APP似乎无法连接到服务器，请检查你的网络连接状况")
        }
    ) {
        TextButton(onClick = {
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            context.startActivity(intent)
        }) {
            Text(text = "打开网络设置")
        }
        TextButton(onClick = {
            retryAction()
        }) {
            Text(text = "重试")
        }
    }
}