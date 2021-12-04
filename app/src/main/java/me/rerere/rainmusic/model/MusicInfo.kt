package me.rerere.rainmusic.model

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import me.rerere.rainmusic.util.media.buildMediaItem
import me.rerere.rainmusic.util.media.metadata

data class MusicInfo(
    val id: Long,
    val name: String,
    val artist: String,
    val musicUrl: String,
    val artworkUrl: String
) {
    fun toMediaItem(): MediaItem = buildMediaItem(id.toString()) {
        metadata {
            setTitle(name)
            setArtist(artist)
            setMediaUri(Uri.parse(musicUrl))
            setArtworkUri(Uri.parse(artworkUrl))
        }
    }

    companion object {
        val PersonalFM = MusicInfo(
            id = 0L,
            name = "私人FM",
            artist = "私人FM",
            musicUrl = "",
            artworkUrl = ""
        )
    }
}