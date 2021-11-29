package me.rerere.rainmusic.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import me.rerere.rainmusic.util.DataState
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class TenApiRepo @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    fun loadYiYan() = flow {
        emit(DataState.Loading)
        try {
            val request = Request.Builder()
                .url("https://tenapi.cn/yiyan/?format=text")
                .get()
                .build()
            val result = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            require(result.isSuccessful)
            val text = result.body?.string() ?: ""
            emit(DataState.Success(text))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}