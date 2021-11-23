package me.rerere.rainmusic.ui.screen.player

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.rerere.rainmusic.repo.MusicRepo
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    musicRepo: MusicRepo
) : ViewModel(){

}