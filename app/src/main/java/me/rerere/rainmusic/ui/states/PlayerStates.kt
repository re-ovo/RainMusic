package me.rerere.rainmusic.ui.states

import androidx.media3.common.Player
import me.rerere.rainmusic.model.MusicInfo

fun Player.startPersonalFM(start: Boolean = true) {
    stop()
    clearMediaItems()
    addMediaItem(
        MusicInfo.PersonalFM.toMediaItem()
    )
    prepare()
    if(start){
        play()
    }
}