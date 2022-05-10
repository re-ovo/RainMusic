package me.rerere.rainmusic.data.retrofit.api.model


import com.google.gson.annotations.SerializedName

data class PlaylistDetail(
    @SerializedName("code")
    val code: Int,
    @SerializedName("playlist")
    val playlist: Playlist,
    @SerializedName("privileges")
    val privileges: List<Privilege>,
    @SerializedName("relatedVideos")
    val relatedVideos: Any,
    @SerializedName("resEntrance")
    val resEntrance: Any,
    @SerializedName("sharedPrivilege")
    val sharedPrivilege: Any,
    @SerializedName("urls")
    val urls: Any
) {
    data class Playlist(
        @SerializedName("adType")
        val adType: Int,
        @SerializedName("backgroundCoverId")
        val backgroundCoverId: Long,
        @SerializedName("backgroundCoverUrl")
        val backgroundCoverUrl: String,
        @SerializedName("cloudTrackCount")
        val cloudTrackCount: Int,
        @SerializedName("commentCount")
        val commentCount: Int,
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
        val creator: Creator,
        @SerializedName("description")
        val description: String,
        @SerializedName("englishTitle")
        val englishTitle: String,
        @SerializedName("highQuality")
        val highQuality: Boolean,
        @SerializedName("historySharedUsers")
        val historySharedUsers: Any,
        @SerializedName("id")
        val id: Long,
        @SerializedName("name")
        val name: String,
        @SerializedName("newImported")
        val newImported: Boolean,
        @SerializedName("officialPlaylistType")
        val officialPlaylistType: String,
        @SerializedName("opRecommend")
        val opRecommend: Boolean,
        @SerializedName("ordered")
        val ordered: Boolean,
        @SerializedName("playCount")
        val playCount: Long,
        @SerializedName("privacy")
        val privacy: Int,
        @SerializedName("remixVideo")
        val remixVideo: Any,
        @SerializedName("shareCount")
        val shareCount: Int,
        @SerializedName("sharedUsers")
        val sharedUsers: Any,
        @SerializedName("specialType")
        val specialType: Int,
        @SerializedName("status")
        val status: Int,
        @SerializedName("subscribed")
        val subscribed: Boolean,
        @SerializedName("subscribedCount")
        val subscribedCount: Int,
        @SerializedName("subscribers")
        val subscribers: List<Any>,
        @SerializedName("tags")
        val tags: List<String>,
        @SerializedName("titleImage")
        val titleImage: Long,
        @SerializedName("titleImageUrl")
        val titleImageUrl: String,
        @SerializedName("trackCount")
        val trackCount: Int,
        @SerializedName("trackIds")
        val trackIds: List<TrackId>,
        @SerializedName("trackNumberUpdateTime")
        val trackNumberUpdateTime: Long,
        @SerializedName("trackUpdateTime")
        val trackUpdateTime: Long,
        @SerializedName("tracks")
        val tracks: List<Track>,
        @SerializedName("updateFrequency")
        val updateFrequency: String,
        @SerializedName("updateTime")
        val updateTime: Long,
        @SerializedName("userId")
        val userId: Long,
        @SerializedName("videoIds")
        val videoIds: Any,
        @SerializedName("videos")
        val videos: Any
    ) {
        data class Creator(
            @SerializedName("accountStatus")
            val accountStatus: Int,
            @SerializedName("anchor")
            val anchor: Boolean,
            @SerializedName("authStatus")
            val authStatus: Int,
            @SerializedName("authenticationTypes")
            val authenticationTypes: Int,
            @SerializedName("authority")
            val authority: Int,
            @SerializedName("avatarDetail")
            val avatarDetail: AvatarDetail,
            @SerializedName("avatarImgId")
            val avatarImgId: Long,
            @SerializedName("avatarImgIdStr")
            val avatarImgIdStr: String,
            @SerializedName("avatarUrl")
            val avatarUrl: String,
            @SerializedName("backgroundImgId")
            val backgroundImgId: Long,
            @SerializedName("backgroundImgIdStr")
            val backgroundImgIdStr: String,
            @SerializedName("backgroundUrl")
            val backgroundUrl: String,
            @SerializedName("birthday")
            val birthday: Int,
            @SerializedName("city")
            val city: Int,
            @SerializedName("defaultAvatar")
            val defaultAvatar: Boolean,
            @SerializedName("description")
            val description: String,
            @SerializedName("detailDescription")
            val detailDescription: String,
            @SerializedName("djStatus")
            val djStatus: Int,
            @SerializedName("expertTags")
            val expertTags: Any,
            @SerializedName("experts")
            val experts: Any,
            @SerializedName("followed")
            val followed: Boolean,
            @SerializedName("gender")
            val gender: Int,
            @SerializedName("mutual")
            val mutual: Boolean,
            @SerializedName("nickname")
            val nickname: String,
            @SerializedName("province")
            val province: Int,
            @SerializedName("remarkName")
            val remarkName: Any,
            @SerializedName("signature")
            val signature: String,
            @SerializedName("userId")
            val userId: Long,
            @SerializedName("userType")
            val userType: Int,
            @SerializedName("vipType")
            val vipType: Int
        ) {
            data class AvatarDetail(
                @SerializedName("identityIconUrl")
                val identityIconUrl: String,
                @SerializedName("identityLevel")
                val identityLevel: Int,
                @SerializedName("userType")
                val userType: Int
            )
        }

        data class TrackId(
            @SerializedName("alg")
            val alg: String,
            @SerializedName("at")
            val at: Long,
            @SerializedName("id")
            val id: Long,
            @SerializedName("rcmdReason")
            val rcmdReason: String,
            @SerializedName("t")
            val t: Int,
            @SerializedName("uid")
            val uid: Long,
            @SerializedName("v")
            val v: Int
        )

        data class Track(
            @SerializedName("a")
            val a: Any,
            @SerializedName("al")
            val al: Al,
            @SerializedName("alg")
            val alg: String,
            @SerializedName("alia")
            val alia: List<String>,
            @SerializedName("ar")
            val ar: List<Ar>,
            @SerializedName("cd")
            val cd: String,
            @SerializedName("cf")
            val cf: String,
            @SerializedName("copyright")
            val copyright: Int,
            @SerializedName("cp")
            val cp: Int,
            @SerializedName("crbt")
            val crbt: Any,
            @SerializedName("djId")
            val djId: Int,
            @SerializedName("dt")
            val dt: Int,
            @SerializedName("fee")
            val fee: Int,
            @SerializedName("ftype")
            val ftype: Int,
            @SerializedName("h")
            val h: H,
            @SerializedName("id")
            val id: Long,
            @SerializedName("l")
            val l: L,
            @SerializedName("m")
            val m: M,
            @SerializedName("mark")
            val mark: Long,
            @SerializedName("mst")
            val mst: Int,
            @SerializedName("mv")
            val mv: Int,
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
            @SerializedName("pop")
            val pop: Double,
            @SerializedName("pst")
            val pst: Int,
            @SerializedName("publishTime")
            val publishTime: Long,
            @SerializedName("rt")
            val rt: String,
            @SerializedName("rtUrl")
            val rtUrl: Any,
            @SerializedName("rtUrls")
            val rtUrls: List<Any>,
            @SerializedName("rtype")
            val rtype: Int,
            @SerializedName("rurl")
            val rurl: Any,
            @SerializedName("s_id")
            val sId: Int,
            @SerializedName("single")
            val single: Int,
            @SerializedName("st")
            val st: Int,
            @SerializedName("t")
            val t: Int,
            @SerializedName("tns")
            val tns: List<String>,
            @SerializedName("v")
            val v: Int
        ) {
            data class Al(
                @SerializedName("id")
                val id: Int,
                @SerializedName("name")
                val name: String,
                @SerializedName("pic")
                val pic: Long,
                @SerializedName("pic_str")
                val picStr: String,
                @SerializedName("picUrl")
                val picUrl: String,
                @SerializedName("tns")
                val tns: List<Any>
            )

            data class Ar(
                @SerializedName("alias")
                val alias: List<Any>,
                @SerializedName("id")
                val id: Int,
                @SerializedName("name")
                val name: String,
                @SerializedName("tns")
                val tns: List<Any>
            )

            data class H(
                @SerializedName("br")
                val br: Int,
                @SerializedName("fid")
                val fid: Int,
                @SerializedName("size")
                val size: Int,
                @SerializedName("vd")
                val vd: Double
            )

            data class L(
                @SerializedName("br")
                val br: Int,
                @SerializedName("fid")
                val fid: Int,
                @SerializedName("size")
                val size: Int,
                @SerializedName("vd")
                val vd: Double
            )

            data class M(
                @SerializedName("br")
                val br: Int,
                @SerializedName("fid")
                val fid: Int,
                @SerializedName("size")
                val size: Int,
                @SerializedName("vd")
                val vd: Double
            )
        }
    }

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
        @SerializedName("paidBigBang")
        val paidBigBang: Boolean,
        @SerializedName("payed")
        val payed: Int,
        @SerializedName("pc")
        val pc: Any,
        @SerializedName("pl")
        val pl: Int,
        @SerializedName("playMaxbr")
        val playMaxbr: Int,
        @SerializedName("preSell")
        val preSell: Boolean,
        @SerializedName("realPayed")
        val realPayed: Int,
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