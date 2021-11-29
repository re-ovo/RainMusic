package me.rerere.rainmusic.retrofit.api.model


import com.google.gson.annotations.SerializedName

data class DailyRecommendSongs(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data
) {
    data class Data(
        @SerializedName("dailySongs")
        val dailySongs: List<DailySong>,
        @SerializedName("orderSongs")
        val orderSongs: List<Any>,
        @SerializedName("recommendReasons")
        val recommendReasons: List<RecommendReason>
    ) {
        data class DailySong(
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
            val id: Int,
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
            @SerializedName("privilege")
            val privilege: Privilege,
            @SerializedName("pst")
            val pst: Int,
            @SerializedName("publishTime")
            val publishTime: Long,
            @SerializedName("reason")
            val reason: String,
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
                val tns: List<String>
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

        data class RecommendReason(
            @SerializedName("reason")
            val reason: String,
            @SerializedName("songId")
            val songId: Int
        )
    }
}