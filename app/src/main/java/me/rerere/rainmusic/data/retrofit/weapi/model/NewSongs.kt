package me.rerere.rainmusic.data.retrofit.weapi.model


import com.google.gson.annotations.SerializedName

data class NewSongs(
    @SerializedName("category")
    val category: Int,
    @SerializedName("code")
    val code: Int,
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
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("picUrl")
        val picUrl: String,
        @SerializedName("song")
        val song: Song,
        @SerializedName("trackNumberUpdateTime")
        val trackNumberUpdateTime: Any,
        @SerializedName("type")
        val type: Int
    ) {
        data class Song(
            @SerializedName("album")
            val album: Album,
            @SerializedName("alias")
            val alias: List<String>,
            @SerializedName("artists")
            val artists: List<Artist>,
            @SerializedName("audition")
            val audition: Any,
            @SerializedName("bMusic")
            val bMusic: BMusic,
            @SerializedName("commentThreadId")
            val commentThreadId: String,
            @SerializedName("copyFrom")
            val copyFrom: String,
            @SerializedName("copyright")
            val copyright: Int,
            @SerializedName("copyrightId")
            val copyrightId: Int,
            @SerializedName("crbt")
            val crbt: Any,
            @SerializedName("dayPlays")
            val dayPlays: Int,
            @SerializedName("disc")
            val disc: String,
            @SerializedName("duration")
            val duration: Int,
            @SerializedName("exclusive")
            val exclusive: Boolean,
            @SerializedName("fee")
            val fee: Int,
            @SerializedName("ftype")
            val ftype: Int,
            @SerializedName("hMusic")
            val hMusic: HMusic,
            @SerializedName("hearTime")
            val hearTime: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("lMusic")
            val lMusic: LMusic,
            @SerializedName("mMusic")
            val mMusic: MMusic,
            @SerializedName("mark")
            val mark: Int,
            @SerializedName("mp3Url")
            val mp3Url: Any,
            @SerializedName("mvid")
            val mvid: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("no")
            val no: Int,
            @SerializedName("noCopyrightRcmd")
            val noCopyrightRcmd: Any,
            @SerializedName("originCoverType")
            val originCoverType: Int,
            @SerializedName("originSongSimpleData")
            val originSongSimpleData: Any,
            @SerializedName("playedNum")
            val playedNum: Int,
            @SerializedName("popularity")
            val popularity: Double,
            @SerializedName("position")
            val position: Int,
            @SerializedName("privilege")
            val privilege: Privilege,
            @SerializedName("ringtone")
            val ringtone: String,
            @SerializedName("rtUrl")
            val rtUrl: Any,
            @SerializedName("rtUrls")
            val rtUrls: List<Any>,
            @SerializedName("rtype")
            val rtype: Int,
            @SerializedName("rurl")
            val rurl: Any,
            @SerializedName("score")
            val score: Int,
            @SerializedName("sign")
            val sign: Any,
            @SerializedName("single")
            val single: Int,
            @SerializedName("starred")
            val starred: Boolean,
            @SerializedName("starredNum")
            val starredNum: Int,
            @SerializedName("status")
            val status: Int,
            @SerializedName("transName")
            val transName: Any
        ) {
            data class Album(
                @SerializedName("alias")
                val alias: List<Any>,
                @SerializedName("artist")
                val artist: Artist,
                @SerializedName("artists")
                val artists: List<Artist>,
                @SerializedName("blurPicUrl")
                val blurPicUrl: String,
                @SerializedName("briefDesc")
                val briefDesc: String,
                @SerializedName("commentThreadId")
                val commentThreadId: String,
                @SerializedName("company")
                val company: String,
                @SerializedName("companyId")
                val companyId: Int,
                @SerializedName("copyrightId")
                val copyrightId: Int,
                @SerializedName("description")
                val description: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("mark")
                val mark: Int,
                @SerializedName("name")
                val name: String,
                @SerializedName("onSale")
                val onSale: Boolean,
                @SerializedName("pic")
                val pic: Long,
                @SerializedName("picId")
                val picId: Long,
                @SerializedName("picId_str")
                val picIdStr: String,
                @SerializedName("picUrl")
                val picUrl: String,
                @SerializedName("publishTime")
                val publishTime: Long,
                @SerializedName("size")
                val size: Int,
                @SerializedName("songs")
                val songs: List<Any>,
                @SerializedName("status")
                val status: Int,
                @SerializedName("subType")
                val subType: String,
                @SerializedName("tags")
                val tags: String,
                @SerializedName("transName")
                val transName: Any,
                @SerializedName("type")
                val type: String
            ) {
                data class Artist(
                    @SerializedName("albumSize")
                    val albumSize: Int,
                    @SerializedName("alias")
                    val alias: List<Any>,
                    @SerializedName("briefDesc")
                    val briefDesc: String,
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("img1v1Id")
                    val img1v1Id: Int,
                    @SerializedName("img1v1Url")
                    val img1v1Url: String,
                    @SerializedName("musicSize")
                    val musicSize: Int,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("picId")
                    val picId: Int,
                    @SerializedName("picUrl")
                    val picUrl: String,
                    @SerializedName("topicPerson")
                    val topicPerson: Int,
                    @SerializedName("trans")
                    val trans: String
                )
            }

            data class Artist(
                @SerializedName("albumSize")
                val albumSize: Int,
                @SerializedName("alias")
                val alias: List<Any>,
                @SerializedName("briefDesc")
                val briefDesc: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("img1v1Id")
                val img1v1Id: Int,
                @SerializedName("img1v1Url")
                val img1v1Url: String,
                @SerializedName("musicSize")
                val musicSize: Int,
                @SerializedName("name")
                val name: String,
                @SerializedName("picId")
                val picId: Int,
                @SerializedName("picUrl")
                val picUrl: String,
                @SerializedName("topicPerson")
                val topicPerson: Int,
                @SerializedName("trans")
                val trans: String
            )

            data class BMusic(
                @SerializedName("bitrate")
                val bitrate: Int,
                @SerializedName("dfsId")
                val dfsId: Int,
                @SerializedName("extension")
                val extension: String,
                @SerializedName("id")
                val id: Long,
                @SerializedName("name")
                val name: Any,
                @SerializedName("playTime")
                val playTime: Int,
                @SerializedName("size")
                val size: Int,
                @SerializedName("sr")
                val sr: Int,
                @SerializedName("volumeDelta")
                val volumeDelta: Double
            )

            data class HMusic(
                @SerializedName("bitrate")
                val bitrate: Int,
                @SerializedName("dfsId")
                val dfsId: Int,
                @SerializedName("extension")
                val extension: String,
                @SerializedName("id")
                val id: Long,
                @SerializedName("name")
                val name: Any,
                @SerializedName("playTime")
                val playTime: Int,
                @SerializedName("size")
                val size: Int,
                @SerializedName("sr")
                val sr: Int,
                @SerializedName("volumeDelta")
                val volumeDelta: Double
            )

            data class LMusic(
                @SerializedName("bitrate")
                val bitrate: Int,
                @SerializedName("dfsId")
                val dfsId: Int,
                @SerializedName("extension")
                val extension: String,
                @SerializedName("id")
                val id: Long,
                @SerializedName("name")
                val name: Any,
                @SerializedName("playTime")
                val playTime: Int,
                @SerializedName("size")
                val size: Int,
                @SerializedName("sr")
                val sr: Int,
                @SerializedName("volumeDelta")
                val volumeDelta: Double
            )

            data class MMusic(
                @SerializedName("bitrate")
                val bitrate: Int,
                @SerializedName("dfsId")
                val dfsId: Int,
                @SerializedName("extension")
                val extension: String,
                @SerializedName("id")
                val id: Long,
                @SerializedName("name")
                val name: Any,
                @SerializedName("playTime")
                val playTime: Int,
                @SerializedName("size")
                val size: Int,
                @SerializedName("sr")
                val sr: Int,
                @SerializedName("volumeDelta")
                val volumeDelta: Double
            )

            data class Privilege(
                @SerializedName("chargeInfoList")
                val chargeInfoList: List<ChargeInfo>,
                @SerializedName("cp")
                val cp: Int,
                @SerializedName("cs")
                val cs: Boolean,
                @SerializedName("dl")
                val dl: Int,
                @SerializedName("downloadMaxbr")
                val downloadMaxbr: Int,
                @SerializedName("fee")
                val fee: Int,
                @SerializedName("fl")
                val fl: Int,
                @SerializedName("flag")
                val flag: Int,
                @SerializedName("freeTrialPrivilege")
                val freeTrialPrivilege: FreeTrialPrivilege,
                @SerializedName("id")
                val id: Int,
                @SerializedName("maxbr")
                val maxbr: Int,
                @SerializedName("payed")
                val payed: Int,
                @SerializedName("pl")
                val pl: Int,
                @SerializedName("playMaxbr")
                val playMaxbr: Int,
                @SerializedName("preSell")
                val preSell: Boolean,
                @SerializedName("rscl")
                val rscl: Any,
                @SerializedName("sp")
                val sp: Int,
                @SerializedName("st")
                val st: Int,
                @SerializedName("subp")
                val subp: Int,
                @SerializedName("toast")
                val toast: Boolean
            ) {
                data class ChargeInfo(
                    @SerializedName("chargeMessage")
                    val chargeMessage: Any,
                    @SerializedName("chargeType")
                    val chargeType: Int,
                    @SerializedName("chargeUrl")
                    val chargeUrl: Any,
                    @SerializedName("rate")
                    val rate: Int
                )

                data class FreeTrialPrivilege(
                    @SerializedName("resConsumable")
                    val resConsumable: Boolean,
                    @SerializedName("userConsumable")
                    val userConsumable: Boolean
                )
            }
        }
    }
}