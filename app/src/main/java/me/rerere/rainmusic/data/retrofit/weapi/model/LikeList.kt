package me.rerere.rainmusic.data.retrofit.weapi.model


import com.google.gson.annotations.SerializedName

data class LikeList(
    @SerializedName("ids")
    val ids: List<Long>
)