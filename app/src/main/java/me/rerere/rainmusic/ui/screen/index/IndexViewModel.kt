package me.rerere.rainmusic.ui.screen.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.rerere.rainmusic.repo.TenApiRepo
import me.rerere.rainmusic.repo.MusicRepo
import me.rerere.rainmusic.repo.UserRepo
import me.rerere.rainmusic.retrofit.api.model.Toplists
import me.rerere.rainmusic.retrofit.api.model.UserPlaylists
import me.rerere.rainmusic.retrofit.weapi.model.NewSongs
import me.rerere.rainmusic.retrofit.weapi.model.PersonalizedPlaylist
import me.rerere.rainmusic.util.DataState
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val musicRepo: MusicRepo,
    private val tenApiRepo: TenApiRepo
): ViewModel() {
    // recommend page
    val personalizedPlaylist: MutableStateFlow<DataState<PersonalizedPlaylist>> = MutableStateFlow(DataState.Empty)
    val personalizedSongs: MutableStateFlow<DataState<NewSongs>> = MutableStateFlow(DataState.Empty)
    val toplist: MutableStateFlow<DataState<Toplists>> = MutableStateFlow(DataState.Empty)
    val yiyan: MutableStateFlow<DataState<String>> = MutableStateFlow(DataState.Empty)

    // library page
    val userPlaylist: MutableStateFlow<DataState<UserPlaylists>> = MutableStateFlow(DataState.Empty)

    fun refreshIndexPage(){
        refreshHotComment()

        musicRepo.getPersonalizedPlaylist(10)
            .onEach {
                personalizedPlaylist.value = it
            }
            .launchIn(viewModelScope)
        musicRepo.getNewSongs()
            .onEach {
                personalizedSongs.value = it
            }
            .launchIn(viewModelScope)
        musicRepo.getTopList()
            .onEach {
                toplist.value = it
            }.launchIn(viewModelScope)
    }

    fun refreshHotComment() {
        tenApiRepo.loadYiYan()
            .onEach {
                yiyan.value = it
            }.launchIn(viewModelScope)
    }

    fun refreshLibraryPage(id: Long) {
        userRepo.getUserPlaylists(
            uid = id,
            limit = 1000
        ).onEach {
            userPlaylist.value = it
        }.launchIn(viewModelScope)
    }
}