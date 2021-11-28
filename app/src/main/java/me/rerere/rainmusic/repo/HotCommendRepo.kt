package me.rerere.rainmusic.repo

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import me.rerere.rainmusic.model.HotComment
import me.rerere.rainmusic.util.DataState
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class HotCommendRepo @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private val gson = Gson()

    fun getHotCommend() = flow {
        emit(DataState.Loading)
        try {
            val request = Request.Builder()
                .url("https://tenapi.cn/comment/")
                .get()
                .build()
            val result = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            require(result.isSuccessful)
            val hotComment = gson.fromJson(result.body!!.string(), HotComment::class.java)
            emit(DataState.Success(hotComment))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}