package me.rerere.rainmusic.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import me.rerere.rainmusic.AppContext
import me.rerere.rainmusic.MainActivity
import me.rerere.rainmusic.R

/**
 * 音乐播放服务
 */
class MusicService : Service() {
    private val binder = MusicBinder()

    override fun onCreate() {
        super.onCreate()

        val notification = NotificationCompat.Builder(this, "music")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCustomContentView(
                RemoteViews(AppContext.instance.packageName, R.layout.music_player).apply {
                    setTextViewText(
                        R.id.musicName,
                        "音乐标题"
                    )
                }
            )
            .setShowWhen(false)
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
        stopForeground(false)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        return this.binder
    }

    inner class MusicBinder : Binder() {
        val musicService: MusicService
            get() = this@MusicService
    }
}