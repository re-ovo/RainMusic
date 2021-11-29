package me.rerere.rainmusic.ui.states

import androidx.compose.runtime.*
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.isActive

@Composable
fun rememberCurrentMediaItem(player: Player?): MediaItem? {
    var mediaItemState by remember(player) {
        mutableStateOf(player?.currentMediaItem)
    }
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                println("item trans: ${mediaItem?.mediaId} reason: $reason")
                mediaItemState = mediaItem
            }
        }
        player?.addListener(listener)
        onDispose {
            player?.removeListener(listener)
        }
    }
    return mediaItemState
}

@Composable
fun rememberPlayProgress(player: Player?): Pair<Long, Long>? {
    return produceState(
        initialValue = player?.let { player.currentPosition to player.duration },
        key1 = player
    ){
        while (isActive) {
            value = player?.let {
                if(player.currentMediaItem == null){
                    0L to 1L
                } else {
                    player.currentPosition to player.duration.coerceAtLeast(1)
                }
            }
            kotlinx.coroutines.delay(500)
        }
    }.value
}

@Composable
fun rememberPlayState(player: Player?): Boolean? {
    var isPlayingState by remember(player) {
        mutableStateOf(player?.isPlaying)
    }
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                isPlayingState = isPlaying
            }
        }
        player?.addListener(listener)
        onDispose {
            player?.removeListener(listener)
        }
    }
    return isPlayingState
}