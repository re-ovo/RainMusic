package me.rerere.rainmusic.data.retrofit.weapi.model
import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("account")
    val account: Account,
    @SerializedName("code")
    val code: Int,
    @SerializedName("loginType")
    val loginType: Int,
    @SerializedName("profile")
    val profile: Profile,
    @SerializedName("token")
    val token: String
) {
    data class Account(
        @SerializedName("anonimousUser")
        val anonimousUser: Boolean,
        @SerializedName("ban")
        val ban: Int,
        @SerializedName("baoyueVersion")
        val baoyueVersion: Int,
        @SerializedName("createTime")
        val createTime: Long,
        @SerializedName("donateVersion")
        val donateVersion: Int,
        @SerializedName("id")
        val id: Long,
        @SerializedName("salt")
        val salt: String,
        @SerializedName("status")
        val status: Int,
        @SerializedName("tokenVersion")
        val tokenVersion: Int,
        @SerializedName("type")
        val type: Int,
        @SerializedName("userName")
        val userName: String,
        @SerializedName("vipType")
        val vipType: Int,
        @SerializedName("viptypeVersion")
        val viptypeVersion: Long,
        @SerializedName("whitelistAuthority")
        val whitelistAuthority: Int
    )

    data class Profile(
        @SerializedName("accountStatus")
        val accountStatus: Int,
        @SerializedName("authStatus")
        val authStatus: Int,
        @SerializedName("authority")
        val authority: Int,
        @SerializedName("avatarDetail")
        val avatarDetail: Any,
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
        val birthday: Long,
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
        @SerializedName("eventCount")
        val eventCount: Int,
        @SerializedName("expertTags")
        val expertTags: Any,
        @SerializedName("experts")
        val experts: Experts,
        @SerializedName("followed")
        val followed: Boolean,
        @SerializedName("followeds")
        val followeds: Int,
        @SerializedName("follows")
        val follows: Int,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("mutual")
        val mutual: Boolean,
        @SerializedName("nickname")
        val nickname: String,
        @SerializedName("playlistBeSubscribedCount")
        val playlistBeSubscribedCount: Int,
        @SerializedName("playlistCount")
        val playlistCount: Int,
        @SerializedName("province")
        val province: Int,
        @SerializedName("remarkName")
        val remarkName: Any,
        @SerializedName("signature")
        val signature: String,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("userType")
        val userType: Int,
        @SerializedName("vipType")
        val vipType: Int
    ) {
        class Experts
    }
}