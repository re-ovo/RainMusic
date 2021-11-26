package me.rerere.rainmusic.retrofit.api.model


import com.google.gson.annotations.SerializedName

data class Lyric(
    @SerializedName("code")
    val code: Int,
    @SerializedName("klyric")
    val klyric: Klyric,
    @SerializedName("lrc")
    val lrc: Lrc,
    @SerializedName("lyricUser")
    val lyricUser: LyricUser,
    @SerializedName("qfy")
    val qfy: Boolean,
    @SerializedName("sfy")
    val sfy: Boolean,
    @SerializedName("sgc")
    val sgc: Boolean,
    @SerializedName("tlyric")
    val tlyric: Tlyric?,
    @SerializedName("transUser")
    val transUser: TransUser
) {
    data class Klyric(
        @SerializedName("lyric")
        val lyric: String,
        @SerializedName("version")
        val version: Int
    )

    data class Lrc(
        @SerializedName("lyric")
        val lyric: String,
        @SerializedName("version")
        val version: Int
    )

    data class LyricUser(
        @SerializedName("demand")
        val demand: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("uptime")
        val uptime: Long,
        @SerializedName("userid")
        val userid: Int
    )

    data class Tlyric(
        @SerializedName("lyric")
        val lyric: String?,
        @SerializedName("version")
        val version: Int
    )

    data class TransUser(
        @SerializedName("demand")
        val demand: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("uptime")
        val uptime: Long,
        @SerializedName("userid")
        val userid: Int
    )
}


fun Lyric.parse(): List<LyricLine> {
    val lines = lrc.lyric.split("\n")
        .filter {
            it.matches(Regex("\\[\\d+:\\d+.\\d+].+"))
        }.map {
            val minutes = it.substring(1 until (it.indexOf(":"))).toIntOrNull() ?: 0
            val seconds =
                it.substring((it.indexOf(":") + 1) until it.indexOf(".")).toIntOrNull() ?: 0
            val time = minutes * 60 + seconds
            LyricLine(
                time = time,
                lyric = it.substring(it.indexOf("]") + 1),
                translation = null
            )
        }

    // 将翻译添加到歌词中
    tlyric?.lyric?.split("\n")?.filter {
        it.matches(Regex("\\[\\d+:\\d+.\\d+].+"))
    }?.forEach {
        val minutes = it.substring(1 until (it.indexOf(":"))).toIntOrNull() ?: 0
        val seconds =
            it.substring((it.indexOf(":") + 1) until it.indexOf(".")).toIntOrNull() ?: 0
        val time = minutes * 60 + seconds
        lines.find { lyric -> lyric.time == time && lyric.translation == null }?.translation = it.substring(it.indexOf("]") + 1)
    }
    return lines
}

data class LyricLine(
    val time: Int, // 歌词时间(秒)
    val lyric: String,
    var translation: String?
)