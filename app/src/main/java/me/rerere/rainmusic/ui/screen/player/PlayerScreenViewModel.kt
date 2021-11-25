package me.rerere.rainmusic.ui.screen.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.rerere.rainmusic.repo.MusicRepo
import me.rerere.rainmusic.retrofit.eapi.NeteaseMusicEApi
import me.rerere.rainmusic.util.encrypt.asPostBody
import me.rerere.rainmusic.util.encrypt.encryptEApi
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    private val musicRepo: MusicRepo
) : ViewModel(){
    init {
        // loadMusic()
    }

    fun loadMusic() {
        musicRepo.getMusicUrl(24615856)
            .onEach {
                println(it.readSafely()?.data?.get(0)?.url)
            }.launchIn(viewModelScope)
    }
}