package me.rerere.rainmusic.retrofit.weapi.model


import com.google.gson.annotations.SerializedName

data class PersonalizedPlaylist(
    @SerializedName("category")
    val category: Int,
    @SerializedName("code")
    val code: Int,
    @SerializedName("hasTaste")
    val hasTaste: Boolean,
    @SerializedName("result")
    val result: List<Result>
) {
    data class Result(
        @SerializedName("alg")
        val alg: String,
        @SerializedName("canDislike")
        val canDislike: Boolean,
        @SerializedName("copywriter")
        val copywriter: Any,
        @SerializedName("highQuality")
        val highQuality: Boolean,
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("picUrl")
        val picUrl: String,
        @SerializedName("playCount")
        val playCount: Double,
        @SerializedName("trackCount")
        val trackCount: Int,
        @SerializedName("trackNumberUpdateTime")
        val trackNumberUpdateTime: Long,
        @SerializedName("type")
        val type: Int
    )
}