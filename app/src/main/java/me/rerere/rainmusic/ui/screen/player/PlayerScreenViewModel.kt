package me.rerere.rainmusic.ui.screen.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.rerere.rainmusic.data.retrofit.api.model.Lyric
import me.rerere.rainmusic.data.retrofit.api.model.MusicDetails
import me.rerere.rainmusic.data.retrofit.weapi.model.LikeList
import me.rerere.rainmusic.repo.MusicRepo
import me.rerere.rainmusic.repo.UserRepo
import me.rerere.rainmusic.util.DataState
import javax.inject.Inject

@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    private val musicRepo: MusicRepo,
    private val userRepo: UserRepo
) : ViewModel() {
    val likeList: MutableStateFlow<DataState<LikeList>> = MutableStateFlow(DataState.Empty)
    val musicDetail: MutableStateFlow<DataState<MusicDetails>> = MutableStateFlow(DataState.Empty)
    val lyric: MutableStateFlow<DataState<Lyric>> = MutableStateFlow(DataState.Empty)

    fun like(uid: Long) {
        viewModelScope.launch {
            if (musicDetail.value is DataState.Success && likeList.value is DataState.Success) {
                val id = musicDetail.value.read().songs[0].id
                val like = likeList.value.read().ids.contains(id)
                musicRepo.likeMusic(
                    musicId = id,
                    like = !like
                )?.let {
                    if(it.code == 200) {
                        loadLikeList(uid)
                    }
                }
            }
        }
    }

    fun loadLikeList(uid: Long) {
        if (uid <= 0) {
            return
        }
        userRepo.getLikeList(uid).onEach {
            likeList.value = it
        }.launchIn(viewModelScope)
    }

    fun loadMusicDetail(id: Long) {
        if (id == 0L) {
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