package me.rerere.rainmusic.ui.screen.playlist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.rerere.rainmusic.repo.MusicRepo
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    musicRepo: MusicRepo
) : ViewModel(){

}