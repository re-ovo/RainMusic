package me.rerere.rainmusic.repo

import kotlinx.coroutines.flow.flow
import me.rerere.rainmusic.retrofit.api.NeteaseMusicApi
import me.rerere.rainmusic.retrofit.eapi.NeteaseMusicEApi
import me.rerere.rainmusic.retrofit.weapi.NeteaseMusicWeApi
import me.rerere.rainmusic.retrofit.weapi.model.HotPlaylistTag
import me.rerere.rainmusic.util.DataState
import me.rerere.rainmusic.util.encrypt.encryptEApi
import me.rerere.rainmusic.util.encrypt.encryptWeAPI
import javax.inject.Inject

class MusicRepo @Inject constructor(
    private val api: NeteaseMusicApi,
    private val eApi: NeteaseMusicEApi,
    private val weApi: NeteaseMusicWeApi
) {
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
        } catch (e: Exception) {
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
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getMusicUrl(
        id: Long,
        bitRate: Int = 999000
    ) = flow {
        emit(DataState.Loading)
        try {
            val result = eApi.getMusicUrl(
                encryptEApi(
                    url = "/api/song/enhance/player/url",
                    data = mapOf(
                        "ids" to "[$id]",
                        "br" to bitRate.toString()
                    )
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getMusicDetail(
        id: Long
    ) = flow {
        emit(DataState.Loading)
        try {
            val result = api.getMusicDetail(
                mapOf(
                    "c" to "[{\"id\":$id}]"
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getLyric(id: Long)= flow {
        emit(DataState.Loading)
        try {
            val result = api.getLyric(
                mapOf(
                    "id" to id.toString(),
                    "lv" to "-1",
                    "kv" to "-1",
                    "tv" to "-1"
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getTopList() = flow {
        emit(DataState.Loading)
        try {
            val result = api.getAllTopList()
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getDailyRecommendSongs() = flow {
        emit(DataState.Loading)
        try {
            val result = api.getDailyRecommendSongList()
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getPlaylistCategory() = flow {
        emit(DataState.Loading)
        try {
            val result = weApi.getPlaylistCat(
                encryptWeAPI(
                    mapOf()
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    fun getTopPlaylist(
        category: String,
        order: String = "hot",
        limit: Int = 50,
        offset: Int = 0
    ) = flow {
        emit(DataState.Loading)
        try {
            val result = weApi.getTopPlaylist(
                encryptWeAPI(
                    mapOf(
                        "cat" to category,
                        "order" to order,
                        "limit" to limit.toString(),
                        "offset" to offset.toString(),
                        "total" to "true"
                    )
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    suspend fun getHotPlaylistTags(): HotPlaylistTag? {
        return try {
            weApi.getHotPlaylistTags(
                encryptWeAPI(
                    mapOf()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getHighQualityPlaylist(
        cat: String,
        limit: Int = 20
    ) = flow {
        emit(DataState.Loading)
        try {
            val result = api.getHighQualityPlaylist(
                mapOf(
                    "cat" to cat,
                    "limit" to limit.toString(),
                    "lasttime" to "0",
                    "total" to "true"
                )
            )
            emit(DataState.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}