package me.rerere.rainmusic.service

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.media3.common.*
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import me.rerere.rainmusic.MainActivity

class MusicService : MediaSessionService() {
    lateinit var player: Player
    lateinit var mediaSession: MediaSession

    override fun onCreate() {
        super.onCreate()

        player = ExoPlayer.Builder(this)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build()

        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE))
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    override fun onDestroy() {
        player.release()
        mediaSession.release()

        super.onDestroy()
    }
}