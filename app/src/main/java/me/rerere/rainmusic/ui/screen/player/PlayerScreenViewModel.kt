package me.rerere.rainmusic.ui.screen.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.rerere.rainmusic.repo.MusicRepo
import me.rerere.rainmusic.retrofit.api.NeteaseMusicApi
import me.rerere.rainmusic.retrofit.api.model.Lyric
import me.rerere.rainmusic.retrofit.api.model.MusicDetails
import me.rerere.rainmusic.retrofit.eapi.NeteaseMusicEApi
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.encrypt.asPostBody
import me.rerere.rainmusic.util.encrypt.encryptEApi
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    private val musicRepo: MusicRepo
) : ViewModel() {
    val musicDetail: MutableStateFlow<DataState<MusicDetails>> = MutableStateFlow(DataState.Empty)
    val lyric: MutableStateFlow<DataState<Lyric>> = MutableStateFlow(DataState.Empty)

    fun loadMusicDetail(id: Long) {
        if(id == 0L){
            musicDetail.value = DataState.Empty
            lyric.value = DataState.Empty
            return
        }

        musicRepo.getMusicDetail(id)
            .onEach {
                musicDetail.value = it
            }.launchIn(viewModelScope)

        musicRepo.getLyric(id)
            .onEach {
                lyric.value = it
            }.launchIn(viewModelScope)
    }
}