package me.rerere.rainmusic.model

import com.google.gson.annotations.SerializedName

data class HotComment(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("album")
        val album: String,
        @SerializedName("content")
        val content: String,
        @SerializedName("cover")
        val cover: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("sing")
        val sing: String,
        @SerializedName("song")
        val song: String,
        @SerializedName("url")
        val url: String
    )
}