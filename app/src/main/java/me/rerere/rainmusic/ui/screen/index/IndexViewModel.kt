package me.rerere.rainmusic.ui.screen.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.rerere.rainmusic.repo.MusicRepo
import me.rerere.rainmusic.repo.YiYanRepo
import me.rerere.rainmusic.repo.UserRepo
import me.rerere.rainmusic.repo.YiYan
import me.rerere.rainmusic.retrofit.api.model.HighQualityPlaylist
import me.rerere.rainmusic.retrofit.api.model.Toplists
import me.rerere.rainmusic.retrofit.api.model.UserPlaylists
import me.rerere.rainmusic.retrofit.weapi.model.NewSongs
import me.rerere.rainmusic.retrofit.weapi.model.PersonalizedPlaylist
import me.rerere.rainmusic.retrofit.weapi.model.PlaylistCategory
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.sharedPreferencesOf
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val userRepo: UserRepo,
    val musicRepo: MusicRepo,
    private val yiYanRepo: YiYanRepo
): ViewModel() {
    // recommend page
    val personalizedPlaylist: MutableStateFlow<DataState<PersonalizedPlaylist>> = MutableStateFlow(DataState.Empty)
    val personalizedSongs: MutableStateFlow<DataState<NewSongs>> = MutableStateFlow(DataState.Empty)
    val toplist: MutableStateFlow<DataState<Toplists>> = MutableStateFlow(DataState.Empty)
    val yiyan: MutableStateFlow<DataState<YiYan>> = MutableStateFlow(DataState.Empty)

    // playlist discover
    val categoryAll: MutableStateFlow<DataState<PlaylistCategory>> = MutableStateFlow(DataState.Empty)
    val categorySelected: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val highQualityPlaylist: MutableStateFlow<DataState<HighQualityPlaylist>> = MutableStateFlow(DataState.Empty)

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

    fun refreshExplorePage() {
        musicRepo.getPlaylistCategory().onEach {
            categoryAll.value = it
        }.launchIn(viewModelScope)

        musicRepo.getHighQualityPlaylist(
            cat = "全部",
            limit = 50
        ).onEach {
            highQualityPlaylist.value = it
        }.launchIn(viewModelScope)

        refreshSelectedCategory()
    }

    fun refreshSelectedCategory(){
        viewModelScope.launch {
            val sharedPreferences = sharedPreferencesOf("playlist_category")
            categorySelected.value = if (sharedPreferences.contains("data")) {
                // 载入用户自定义歌单category
                (sharedPreferences.getString("data", "")?.split(",") ?: emptyList())
            } else {
                // 载入热门category
                musicRepo.getHotPlaylistTags()?.tags?.map { it.name } ?: emptyList()
            }
        }
    }

    fun refreshHotComment() {
        yiYanRepo.loadYiYan()
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