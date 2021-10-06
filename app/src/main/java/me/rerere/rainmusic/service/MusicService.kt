package me.rerere.rainmusic.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import me.rerere.rainmusic.MainActivity
import me.rerere.rainmusic.R
import me.rerere.rainmusic.util.createNotificationChannel

/**
 * 音乐播放服务
 */
class MusicService : Service() {
    override fun onCreate() {
        super.onCreate()

        createNotificationChannel(
            channelId = "music",
            name = "音乐播放器通知栏组件", // TODO: 本地化
            description = "请勿禁用该通知" // TODO: 本地化
        )

        val notification = NotificationCompat.Builder(this, "music")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("前台服务")
            .setContentText("这是一个前台服务")
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(
                        this,
                        MainActivity::class.java
                    ),
                    PendingIntent.FLAG_IMMUTABLE
                )
            ).build()

        startForeground(
            1,
            notification
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("服务已启动: ${this.toString()}")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return MusicBinder()
    }

    inner class MusicBinder : Binder() {

    }
}