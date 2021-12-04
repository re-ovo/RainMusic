package me.rerere.rainmusic.service

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.extractor.DefaultExtractorsFactory
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import me.rerere.rainmusic.RouteActivity
import me.rerere.rainmusic.repo.MusicRepo
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.RainMusicProtocol
import me.rerere.rainmusic.util.okhttp.https
import java.io.IOException
import javax.inject.Inject

private const val TAG = "MusicService"

@AndroidEntryPoint
class MusicService : MediaLibraryService() {
    @Inject
    lateinit var musicRepo: MusicRepo
    private val lifecycleScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    lateinit var player: Player
    lateinit var mediaSession: MediaLibrarySession

    override fun onCreate() {
        super.onCreate()

        player = ExoPlayer.Builder(this)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(
                    // 自定义datasource
                    ResolvingDataSource.Factory(DefaultDataSource.Factory(this), NeteaseMusicResolver()),
                    DefaultExtractorsFactory()
                ),
            )
            .setWakeMode(C.WAKE_MODE_LOCAL)
            .build()

        player.repeatMode = Player.REPEAT_MODE_ALL

        mediaSession = MediaLibrarySession.Builder(this, player, LibrarySessionCallback())
            .setMediaItemFiller(CustomMediaItemFiller())
            .setSessionActivity(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, RouteActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
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
            return mediaItem.buildUpon()
                .setUri(mediaItem.mediaMetadata.mediaUri)
                .build()
        }
    }

    class LibrarySessionCallback : MediaLibrarySession.MediaLibrarySessionCallback {

    }

    inner class NeteaseMusicResolver : ResolvingDataSource.Resolver {
        override fun resolveDataSpec(dataSpec: DataSpec): DataSpec {
            // 动态解析歌曲地址
            if(dataSpec.uri.scheme == RainMusicProtocol && dataSpec.uri.host == "music"){
                val musicId = dataSpec.uri.getQueryParameter("id")?.toLong() ?: error("can't find music id")
                Log.i(TAG, "resolveDataSpec: 开始解析歌曲($musicId)的播放地址")
                val url = runBlocking {
                    var musicUrl = ""
                    musicRepo.getMusicUrl(musicId).collect {
                        if(it is DataState.Success && musicUrl.isBlank()){
                            musicUrl = it.readSafely()?.data?.get(0)?.url ?: ""
                        }
                    }
                    musicUrl
                }
                Log.i(TAG, "resolveDataSpec: 解析完成: $url")
                if(url.isBlank()){
                    lifecycleScope.launch {
                        if (player.hasNextMediaItem()) {
                            player.seekToNextMediaItem()
                            player.prepare()
                            player.play()
                        } else {
                            throw IOException("无法解析Music URL")
                        }
                    }
                }
                return dataSpec.buildUpon()
                    .apply {
                        if(url.isNotBlank()){
                            setUri(Uri.parse(url.https))
                        }
                    }
                    .build()
            }
            return dataSpec
        }
    }
}