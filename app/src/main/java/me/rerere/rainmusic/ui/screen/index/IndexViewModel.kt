package me.rerere.rainmusic.ui.screen.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.rerere.rainmusic.repo.UserRepo
import me.rerere.rainmusic.retrofit.api.model.AccountDetail
import me.rerere.rainmusic.util.DataState
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
    val accountDetail: MutableStateFlow<DataState<AccountDetail>> = MutableStateFlow(DataState.Empty)

    fun refreshAccountDetail() {
        userRepo.getAccountDetail()
            .onEach {
                accountDetail.value = it
            }
            .launchIn(viewModelScope)
    }

    init {
        refreshAccountDetail()
    }
}