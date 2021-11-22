package me.rerere.rainmusic.ui.screen.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import me.rerere.rainmusic.repo.UserRepo
import me.rerere.rainmusic.util.encrypt.asPostBody
import me.rerere.rainmusic.util.encrypt.encryptEApi
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val okHttpClient: OkHttpClient
): ViewModel() {
    fun refresh() {
        userRepo.refreshLogin().launchIn(viewModelScope)
    }
}