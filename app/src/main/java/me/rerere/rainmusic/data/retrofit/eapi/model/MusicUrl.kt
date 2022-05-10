package me.rerere.rainmusic.data.retrofit.eapi.model


import com.google.gson.annotations.SerializedName

data class MusicUrl(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>
) {
    data class Data(
        @SerializedName("br")
        val br: Int,
        @SerializedName("canExtend")
        val canExtend: Boolean,
        @SerializedName("code")
        val code: Int,
        @SerializedName("encodeType")
        val encodeType: Any,
        @SerializedName("expi")
        val expi: Int,
        @SerializedName("fee")
        val fee: Int,
        @SerializedName("flag")
        val flag: Int,
        @SerializedName("freeTimeTrialPrivilege")
        val freeTimeTrialPrivilege: FreeTimeTrialPrivilege,
        @SerializedName("freeTrialInfo")
        val freeTrialInfo: Any,
        @SerializedName("freeTrialPrivilege")
        val freeTrialPrivilege: FreeTrialPrivilege,
        @SerializedName("gain")
        val gain: Double,
        @SerializedName("id")
        val id: Int,
        @SerializedName("level")
        val level: Any,
        @SerializedName("md5")
        val md5: String,
        @SerializedName("payed")
        val payed: Int,
        @SerializedName("size")
        val size: Int,
        @SerializedName("type")
        val type: String,
        @SerializedName("uf")
        val uf: Any,
        @SerializedName("url")
        val url: String,
        @SerializedName("urlSource")
        val urlSource: Int
    ) {
        data class FreeTimeTrialPrivilege(
            @SerializedName("remainTime")
            val remainTime: Int,
            @SerializedName("resConsumable")
            val resConsumable: Boolean,
            @SerializedName("type")
            val type: Int,
            @SerializedName("userConsumable")
            val userConsumable: Boolean
        )

        data class FreeTrialPrivilege(
            @SerializedName("resConsumable")
            val resConsumable: Boolean,
            @SerializedName("userConsumable")
            val userConsumable: Boolean
        )
    }
}