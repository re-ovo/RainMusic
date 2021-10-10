package me.rerere.rainmusic.retrofit.weapi

import me.rerere.rainmusic.retrofit.weapi.response.LoginResponse
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
    @POST("weapi/login/cellphone")
    @FormUrlEncoded
    suspend fun loginCellphone(
        @FieldMap body: Map<String, String>
    ) : LoginResponse
}