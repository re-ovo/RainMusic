package me.rerere.rainmusic.util.media

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata

/**
 * 使用DSL构建MediaItem
 *
 * @param mediaId MediaId
 * @param buildScope MediaItem构建lambda
 */
fun buildMediaItem(mediaId: String, buildScope: MediaItem.Builder.() -> Unit): MediaItem =
    MediaItem.Builder()
        .setMediaId(mediaId)
        .apply(buildScope)
        .build()

/**
 * 使用DSL构建 MediaMetadata
 *
 * @param scope MediaMetadata构建器
 */
fun MediaItem.Builder.metadata(scope: MediaMetadata.Builder.() -> Unit) {
    setMediaMetadata(MediaMetadata.Builder().apply(scope).build())
}