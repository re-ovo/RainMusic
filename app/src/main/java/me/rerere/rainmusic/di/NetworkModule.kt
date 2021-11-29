package me.rerere.rainmusic.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.rerere.rainmusic.retrofit.api.NeteaseMusicApi
import me.rerere.rainmusic.retrofit.eapi.NeteaseMusicEApi
import me.rerere.rainmusic.retrofit.weapi.NeteaseMusicWeApi
import me.rerere.rainmusic.util.okhttp.CookieHelper
import me.rerere.rainmusic.util.okhttp.EnsureHttpsInterceptor
import me.rerere.rainmusic.util.okhttp.UserAgentInterceptor
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
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(EnsureHttpsInterceptor())
        .addInterceptor(UserAgentInterceptor()) // user-agent 拦截
        .cookieJar(CookieHelper) // cookie
        .build()

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://music.163.com")
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideNeteaseWeApi(
        retrofit: Retrofit
    ): NeteaseMusicWeApi = retrofit.create(
        NeteaseMusicWeApi::class.java
    )

    @Singleton
    @Provides
    fun provideNeteaseApi(
        retrofit: Retrofit
    ): NeteaseMusicApi = retrofit.create(
        NeteaseMusicApi::class.java
    )

    @Singleton
    @Provides
    fun provideNeteaseEApi(okHttpClient: OkHttpClient): NeteaseMusicEApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://interface3.music.163.com")
        .client(okHttpClient)
        .build()
        .create(NeteaseMusicEApi::class.java)
}