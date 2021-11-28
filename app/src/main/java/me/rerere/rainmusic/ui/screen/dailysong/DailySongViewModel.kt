package me.rerere.rainmusic.ui.screen.dailysong

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.rerere.rainmusic.repo.MusicRepo
import me.rerere.rainmusic.retrofit.api.model.DailyRecommendSongs
import me.rerere.rainmusic.util.DataState
import javax.inject.Inject

@HiltViewModel
class DailySongViewModel @Inject constructor(
    private val musicRepo: MusicRepo
) : ViewModel() {
    val dailySongs: MutableStateFlow<DataState<DailyRecommendSongs>> = MutableStateFlow(DataState.Empty)

    init {
        musicRepo.getDailyRecommendSongs()
            .onEach {
                dailySongs.value = it
            }.launchIn(viewModelScope)
    }
}