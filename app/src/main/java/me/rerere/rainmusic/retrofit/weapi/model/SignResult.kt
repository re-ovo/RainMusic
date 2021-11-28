package me.rerere.rainmusic.retrofit.weapi.model

import com.google.gson.annotations.SerializedName

data class SignResult(
    @SerializedName("code") val code: Int,
    @SerializedName("point") val point: Int?,
    @SerializedName("msg") val msg: String?
)