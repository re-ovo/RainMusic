package me.rerere.rainmusic.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipDescription
import android.content.Context
import android.os.Build
import android.widget.Toast

/**
 * 快速创建通知渠道
 *
 * @param channelId 渠道ID
 * @param name 渠道名称
 * @param importance 渠道通知的重要程度
 * @param description 渠道描述
 */
@SuppressLint("WrongConstant")
fun Context.createNotificationChannel(
    channelId: String,
    name: String,
    importance: Int = 2, // 2 = Low
    description: String? = null,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        (this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
            createNotificationChannel(
                NotificationChannel(
                    channelId,
                    name,
                    importance
                ).apply {
                    description?.let {
                        this.description = description
                    }
                }
            )
        }
    }
}

/**
 * 快速发送toast通知
 *
 * @param text 通知内容
 * @param duration 通知长短
 */
fun Context.toast(
    text: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(
        this,
        text,
        duration
    ).show()
}