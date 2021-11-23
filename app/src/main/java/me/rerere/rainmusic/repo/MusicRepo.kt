package me.rerere.rainmusic.repo

import kotlinx.coroutines.flow.flow
import me.rerere.rainmusic.retrofit.api.NeteaseMusicApi
import me.rerere.rainmusic.retrofit.weapi.NeteaseMusicWeApi
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.encrypt.encryptWeAPI
import javax.inject.Inject

class MusicRepo @Inject constructor(
    private val api: NeteaseMusicApi,
    private val weApi: NeteaseMusicWeApi
){
    fun getPersonalizedPlaylist(
        limit: Int = 10
    ) = flow {
        emit(DataState.Loading)
        try {
            val result = weApi.personalizedPlaylist(
                encryptWeAPI(
                    mapOf(
                        "limit" to limit.toString(),
                        "total" to "true",
                        "n" to "1000"
                    )
                )
            )
            emit(DataState.Success(result))
        }catch (e: Exception){
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getNewSongs(
        limit: Int = 10,
        areaId: Int = 0
    ) = flow {
        emit(DataState.Loading)
        try {
            val result = weApi.getNewSongs(
                encryptWeAPI(
                    mapOf(
                        "type" to "recommend",
                        "limit" to limit.toString(),
                        "areaId" to areaId.toString()
                    )
                )
            )
            emit(DataState.Success(result))
        }catch (e: Exception){
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getPlaylistDetail(
        id: Long
    ) = flow {
        emit(DataState.Loading)
        try {
            val result = api.getPlaylistDetail(
                mapOf(
                    "id" to id.toString(),
                    "n" to "100000",
                    "s" to "8"
                )
            )
            emit(DataState.Success(result))
        }catch (e: Exception){
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}