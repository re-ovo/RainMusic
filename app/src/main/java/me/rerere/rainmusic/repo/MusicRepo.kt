package me.rerere.rainmusic.repo

import me.rerere.rainmusic.retrofit.weapi.NeteaseMusicWeApi
import javax.inject.Inject

class MusicRepo @Inject constructor(
    private val weApi: NeteaseMusicWeApi
){

}