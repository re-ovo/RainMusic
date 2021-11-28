package me.rerere.rainmusic.retrofit.weapi

import com.google.gson.JsonObject
import me.rerere.rainmusic.retrofit.weapi.model.*
import me.rerere.rainmusic.util.encrypt.encryptWeAPI
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 网易云weapi接口
 * 访问此接口需要经过 [encryptWeAPI][me.rerere.rainmusic.util.encrypt.encryptWeAPI] 函数加密
 */
interface NeteaseMusicWeApi {
    /**
     * 登录网易云
     */
    @POST("/weapi/login/cellphone")
    @FormUrlEncoded
    suspend fun loginCellphone(
        @FieldMap body: Map<String, String>
    ) : LoginResponse

    /**
     * 刷新登录状态
     */
    @POST("/weapi/login/token/refresh")
    @FormUrlEncoded
    suspend fun refreshLogin(
        @FieldMap body: Map<String, String> = encryptWeAPI()
    ) : JsonObject

    /**
     * 推荐歌单
     */
    @POST("/weapi/personalized/playlist")
    @FormUrlEncoded
    suspend fun personalizedPlaylist(
        @FieldMap body: Map<String, String>
    ): PersonalizedPlaylist

    /**
     * 最新歌曲
     */
    @POST("/weapi/personalized/newsong")
    @FormUrlEncoded
    suspend fun getNewSongs(
        @FieldMap body: Map<String, String>
    ): NewSongs

    /**
     * 喜欢的歌曲列表
     */
    @POST("/weapi/song/like/get")
    @FormUrlEncoded
    suspend fun getLikeList(
        @FieldMap body: Map<String, String>
    ): LikeList

    @POST("/weapi/point/dailyTask")
    @FormUrlEncoded
    suspend fun dailySign(
        @FieldMap body: Map<String, String>
    ): SignResult
}