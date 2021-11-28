package me.rerere.rainmusic

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import me.rerere.rainmusic.util.createNotificationChannel

@HiltAndroidApp
class AppContext : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        // 注册channel
        createNotificationChannel(
            channelId = "music",
            name = "音乐播放器通知栏组件", // TODO: 本地化
            description = "请勿禁用该通知", // TODO: 本地化
            importance = NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    companion object {
        @JvmStatic
        lateinit var instance: AppContext
    }
}

fun sharedPreferencesOf(
    name: String
): SharedPreferences = AppContext.instance.getSharedPreferences(name, Context.MODE_PRIVATE)