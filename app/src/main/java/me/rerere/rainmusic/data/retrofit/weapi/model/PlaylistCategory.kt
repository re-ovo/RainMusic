package me.rerere.rainmusic.data.retrofit.weapi.model


import com.google.gson.annotations.SerializedName

data class PlaylistCategory(
    @SerializedName("all")
    val all: All,
    @SerializedName("categories")
    val categories: Map<String, String>,
    @SerializedName("code")
    val code: Int,
    @SerializedName("sub")
    val sub: List<Sub>
) {
    data class All(
        @SerializedName("activity")
        val activity: Boolean,
        @SerializedName("category")
        val category: Int,
        @SerializedName("hot")
        val hot: Boolean,
        @SerializedName("imgId")
        val imgId: Int,
        @SerializedName("imgUrl")
        val imgUrl: Any,
        @SerializedName("name")
        val name: String,
        @SerializedName("resourceCount")
        val resourceCount: Int,
        @SerializedName("resourceType")
        val resourceType: Int,
        @SerializedName("type")
        val type: Int
    )

    data class Sub(
        @SerializedName("activity")
        val activity: Boolean,
        @SerializedName("category")
        val category: Int,
        @SerializedName("hot")
        val hot: Boolean,
        @SerializedName("imgId")
        val imgId: Int,
        @SerializedName("imgUrl")
        val imgUrl: Any,
        @SerializedName("name")
        val name: String,
        @SerializedName("resourceCount")
        val resourceCount: Int,
        @SerializedName("resourceType")
        val resourceType: Int,
        @SerializedName("type")
        val type: Int
    )
}