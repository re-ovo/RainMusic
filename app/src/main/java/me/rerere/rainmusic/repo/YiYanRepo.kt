package me.rerere.rainmusic.repo

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import me.rerere.rainmusic.util.DataState
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class YiYanRepo @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    fun loadYiYan() = flow {
        emit(DataState.Loading)
        try {
            val request = Request.Builder()
                .url("https://v1.hitokoto.cn")
                .get()
                .build()
            val result = withContext(Dispatchers.IO) {
                okHttpClient.newCall(request).execute()
            }
            require(result.isSuccessful)
            val text = result.body?.string() ?: ""
            emit(DataState.Success(Gson().fromJson(text, YiYan::class.java)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}

data class YiYan(
    @SerializedName("commit_from")
    val commitFrom: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("creator")
    val creator: String,
    @SerializedName("creator_uid")
    val creatorUid: Int,
    @SerializedName("from")
    val from: String,
    @SerializedName("from_who")
    val fromWho: Any,
    @SerializedName("hitokoto")
    val hitokoto: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("length")
    val length: Int,
    @SerializedName("reviewer")
    val reviewer: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uuid")
    val uuid: String
)