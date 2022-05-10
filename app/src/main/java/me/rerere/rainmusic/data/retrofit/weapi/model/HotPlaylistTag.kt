package me.rerere.rainmusic.data.retrofit.weapi.model


import com.google.gson.annotations.SerializedName

data class HotPlaylistTag(
    @SerializedName("code")
    val code: Int,
    @SerializedName("tags")
    val tags: List<Tag>
) {
    data class Tag(
        @SerializedName("activity")
        val activity: Boolean,
        @SerializedName("category")
        val category: Int,
        @SerializedName("createTime")
        val createTime: Long,
        @SerializedName("hot")
        val hot: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("playlistTag")
        val playlistTag: PlaylistTag,
        @SerializedName("position")
        val position: Int,
        @SerializedName("type")
        val type: Int,
        @SerializedName("usedCount")
        val usedCount: Int
    ) {
        data class PlaylistTag(
            @SerializedName("category")
            val category: Int,
            @SerializedName("createTime")
            val createTime: Long,
            @SerializedName("highQuality")
            val highQuality: Int,
            @SerializedName("highQualityPos")
            val highQualityPos: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("officialPos")
            val officialPos: Int,
            @SerializedName("position")
            val position: Int,
            @SerializedName("type")
            val type: Int,
            @SerializedName("usedCount")
            val usedCount: Int
        )
    }
}