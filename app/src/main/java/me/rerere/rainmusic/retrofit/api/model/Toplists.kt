package me.rerere.rainmusic.retrofit.api.model


import com.google.gson.annotations.SerializedName

data class Toplists(
    @SerializedName("artistToplist")
    val artistToplist: ArtistToplist,
    @SerializedName("code")
    val code: Int,
    @SerializedName("list")
    val list: List<TopList>
) {
    data class ArtistToplist(
        @SerializedName("coverUrl")
        val coverUrl: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("position")
        val position: Int,
        @SerializedName("upateFrequency")
        val upateFrequency: String,
        @SerializedName("updateFrequency")
        val updateFrequency: String
    )

    data class TopList(
        @SerializedName("adType")
        val adType: Int,
        @SerializedName("anonimous")
        val anonimous: Boolean,
        @SerializedName("artists")
        val artists: Any,
        @SerializedName("backgroundCoverId")
        val backgroundCoverId: Int,
        @SerializedName("backgroundCoverUrl")
        val backgroundCoverUrl: Any,
        @SerializedName("cloudTrackCount")
        val cloudTrackCount: Int,
        @SerializedName("commentThreadId")
        val commentThreadId: String,
        @SerializedName("coverImgId")
        val coverImgId: Long,
        @SerializedName("coverImgId_str")
        val coverImgIdStr: String,
        @SerializedName("coverImgUrl")
        val coverImgUrl: String,
        @SerializedName("createTime")
        val createTime: Long,
        @SerializedName("creator")
        val creator: Any,
        @SerializedName("description")
        val description: String,
        @SerializedName("englishTitle")
        val englishTitle: Any,
        @SerializedName("highQuality")
        val highQuality: Boolean,
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("newImported")
        val newImported: Boolean,
        @SerializedName("opRecommend")
        val opRecommend: Boolean,
        @SerializedName("ordered")
        val ordered: Boolean,
        @SerializedName("playCount")
        val playCount: Long,
        @SerializedName("privacy")
        val privacy: Int,
        @SerializedName("recommendInfo")
        val recommendInfo: Any,
        @SerializedName("specialType")
        val specialType: Int,
        @SerializedName("status")
        val status: Int,
        @SerializedName("subscribed")
        val subscribed: Any,
        @SerializedName("subscribedCount")
        val subscribedCount: Int,
        @SerializedName("subscribers")
        val subscribers: List<Any>,
        @SerializedName("tags")
        val tags: List<String>,
        @SerializedName("titleImage")
        val titleImage: Int,
        @SerializedName("titleImageUrl")
        val titleImageUrl: Any,
        @SerializedName("ToplistType")
        val toplistType: String,
        @SerializedName("totalDuration")
        val totalDuration: Int,
        @SerializedName("trackCount")
        val trackCount: Int,
        @SerializedName("trackNumberUpdateTime")
        val trackNumberUpdateTime: Long,
        @SerializedName("trackUpdateTime")
        val trackUpdateTime: Long,
        @SerializedName("tracks")
        val tracks: Any,
        @SerializedName("updateFrequency")
        val updateFrequency: String,
        @SerializedName("updateTime")
        val updateTime: Long,
        @SerializedName("userId")
        val userId: Long
    )
}