package me.rerere.rainmusic.retrofit.api

import me.rerere.rainmusic.retrofit.api.model.AccountDetail
import retrofit2.http.GET

interface NeteaseMusicApi {
    /**
     * 获取自己的账号信息
     */
    @GET("/api/nuser/account/get")
    suspend fun getAccountDetail(): AccountDetail
}