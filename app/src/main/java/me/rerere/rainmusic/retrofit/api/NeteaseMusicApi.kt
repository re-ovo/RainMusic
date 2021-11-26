package me.rerere.rainmusic.retrofit.api

import me.rerere.rainmusic.retrofit.api.model.AccountDetail
import me.rerere.rainmusic.retrofit.api.model.Lyric
import me.rerere.rainmusic.retrofit.api.model.MusicDetails
import me.rerere.rainmusic.retrofit.api.model.PlaylistDetail
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface NeteaseMusicApi {
    /**
     * 获取自己的账号信息
     */
    @GET("/api/nuser/account/get")
    suspend fun getAccountDetail(): AccountDetail

    /**
     * 获取歌单信息
     */
    @POST("/api/v6/playlist/detail")
    @FormUrlEncoded
    suspend fun getPlaylistDetail(
        @FieldMap body: Map<String, String>
    ): PlaylistDetail

    @POST("/api/v3/song/detail")
    @FormUrlEncoded
    suspend fun getMusicDetail(
        @FieldMap body: Map<String, String>
    ): MusicDetails

    @POST("/api/song/lyric")
    @FormUrlEncoded
    suspend fun getLyric(
        @FieldMap body: Map<String, String>
    ): Lyric
}