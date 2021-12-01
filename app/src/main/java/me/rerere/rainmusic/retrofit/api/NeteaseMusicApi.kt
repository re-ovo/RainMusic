package me.rerere.rainmusic.retrofit.api

import me.rerere.rainmusic.retrofit.api.model.*
import retrofit2.http.*

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

    /**
     * 获取歌曲详情
     */
    @POST("/api/v3/song/detail")
    @FormUrlEncoded
    suspend fun getMusicDetail(
        @FieldMap body: Map<String, String>
    ): MusicDetails

    /**
     * 获取歌词
     */
    @POST("/api/song/lyric")
    @FormUrlEncoded
    suspend fun getLyric(
        @FieldMap body: Map<String, String>
    ): Lyric

    /**
     * 获取用户的歌单
     */
    @POST("/api/user/playlist")
    @FormUrlEncoded
    suspend fun getUserPlaylist(
        @FieldMap body: Map<String, String>
    ): UserPlaylists

    /**
     * 获取榜单
     */
    @GET("/api/toplist")
    suspend fun getAllTopList(): Toplists

    /**
     * 每日推荐歌曲
     */
    @GET("/api/v3/discovery/recommend/songs")
    suspend fun getDailyRecommendSongList(): DailyRecommendSongs

    /**
     * 获取精品歌单
     */
    @POST("/api/playlist/highquality/list")
    @FormUrlEncoded
    suspend fun getHighQualityPlaylist(
        @FieldMap body: Map<String, String>
    ): HighQualityPlaylist

    @POST("/api/radio/like")
    @FormUrlEncoded
    suspend fun like(
        @Header("like") like: Boolean,
        @FieldMap body: Map<String, String>
    ): LikeResult
}