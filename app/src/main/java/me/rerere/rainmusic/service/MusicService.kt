package me.rerere.rainmusic.service

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.media3.common.*
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.rerere.rainmusic.MainActivity
import me.rerere.rainmusic.repo.MusicRepo
import javax.inject.Inject

class MusicService : MediaLibraryService() {
    @Inject lateinit var musicRepo: MusicRepo
    private val lifecycleScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    lateinit var player: Player
    lateinit var mediaSession: MediaLibrarySession

    override fun onCreate() {
        super.onCreate()

        player = ExoPlayer.Builder(this)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .build()

        mediaSession = MediaLibrarySession.Builder(this, player, LibrarySessionCallback())
            .setMediaItemFiller(CustomMediaItemFiller())
            .setSessionActivity(PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE))
            .build()
    }

    override fun onDestroy() {
        lifecycleScope.cancel()

        player.release()
        mediaSession.release()

        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession {
        return mediaSession
    }

    inner class CustomMediaItemFiller : MediaSession.MediaItemFiller {
        override fun fillInLocalConfiguration(
            session: MediaSession,
            controller: MediaSession.ControllerInfo,
            mediaItem: MediaItem
        ): MediaItem {
            return MediaItem.Builder()
                .setMediaId(mediaItem.mediaId)
                .setUri(mediaItem.mediaMetadata.mediaUri)
                .setMediaMetadata(mediaItem.mediaMetadata)
                .build()
        }
    }

    class LibrarySessionCallback : MediaLibrarySession.MediaLibrarySessionCallback {

    }
}