package me.rerere.rainmusic.repo

import com.soywiz.krypto.md5
import kotlinx.coroutines.flow.flow
import me.rerere.rainmusic.retrofit.api.NeteaseMusicApi
import me.rerere.rainmusic.retrofit.weapi.NeteaseMusicWeApi
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.encrypt.encryptWeAPI
import javax.inject.Inject

private const val TAG = "UserRepo"

class UserRepo @Inject constructor(
    private val api: NeteaseMusicApi,
    private val weApi: NeteaseMusicWeApi
) {
    fun refreshLogin() = flow {
        emit(DataState.Loading)
        try {
            val result = weApi.refreshLogin()
            require(result.get("code").asInt != 301)
            emit(DataState.Success(Unit))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun loginCellPhone(
        phone: String,
        password: String
    ) = flow {
        emit(DataState.Loading)
        kotlinx.coroutines.delay(500) // 等待1秒，防止登录对话框来不及显示
        try {
            val result = weApi.loginCellphone(
                encryptWeAPI(
                    mapOf(
                        "phone" to phone,
                        "countrycode" to "86",
                        "password" to password.toByteArray().md5().hex,
                        "rememberLogin" to "true"
                    )
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getAccountDetail() = flow {
        emit(DataState.Loading)
        try {
            val result = api.getAccountDetail()

            require(result.profile != null)
            require(result.account != null)

            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getLikeList(uid: Long) = flow {
        emit(DataState.Loading)
        try {
            val result = weApi.getLikeList(
                encryptWeAPI(
                    mapOf(
                        "uid" to uid.toString()
                    )
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getUserPlaylists(
        uid: Long,
        limit: Int = 10
    ) = flow {
        emit(DataState.Loading)
        try {
            val result = api.getUserPlaylist(
                mapOf(
                    "uid" to uid.toString(),
                    "limit" to limit.toString(),
                    "includeVideo" to "false"
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun dailySign() = flow {
        emit(DataState.Loading)
        try {
            val result = weApi.dailySign(
                encryptWeAPI(
                    mapOf(
                        "type" to "0" // Android 签到
                    )
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}