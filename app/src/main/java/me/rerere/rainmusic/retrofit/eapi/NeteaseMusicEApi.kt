package me.rerere.rainmusic.retrofit.eapi

import com.google.gson.JsonObject
import me.rerere.rainmusic.retrofit.eapi.model.MusicUrl
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface NeteaseMusicEApi {
    @POST("/eapi/song/enhance/player/url")
    @FormUrlEncoded
    suspend fun getMusicUrl(@FieldMap body: Map<String, String>): MusicUrl

    @POST("/eapi/pl/count")
    @FormUrlEncoded
    suspend fun count(@FieldMap body: Map<String, String>): JsonObject
}