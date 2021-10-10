package me.rerere.rainmusic.di

import android.util.Log
import android.webkit.WebSettings
import androidx.core.content.edit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.rerere.rainmusic.AppContext
import me.rerere.rainmusic.retrofit.weapi.NeteaseMusicWeApi
import me.rerere.rainmusic.sharedPreferencesOf
import me.rerere.rainmusic.util.okhttp.CookieHelper
import me.rerere.rainmusic.util.okhttp.UserAgentInterceptor
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TAG = "NetworkModule"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient() = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(UserAgentInterceptor()) // user-agent 拦截
        .cookieJar(CookieHelper) // cookie
        .build()

    @Singleton
    @Provides
    fun provideNeateaseMusicService(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://music.163.com/")
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideNeteaseService(
        retrofit: Retrofit
    ): NeteaseMusicWeApi = retrofit.create(
        NeteaseMusicWeApi::class.java
    )
}