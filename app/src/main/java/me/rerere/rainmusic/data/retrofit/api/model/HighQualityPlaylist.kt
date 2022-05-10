package me.rerere.rainmusic.data.retrofit.api.model

import com.google.gson.annotations.SerializedName
import me.rerere.rainmusic.data.model.Playlists

data class HighQualityPlaylist(
    @SerializedName("code")
    val code: Int,
    @SerializedName("lasttime")
    val lasttime: Long,
    @SerializedName("more")
    val more: Boolean,
    @SerializedName("playlists")
    val playlists: List<Playlists>,
    @SerializedName("total")
    val total: Int
)