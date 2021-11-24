package me.rerere.rainmusic.util.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryHelper(
    private val maxRetryTimes: Int = 3
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            null
        }
        var retryNum = 0
        while (response?.isSuccessful != true && retryNum < maxRetryTimes) {
            retryNum++
            Log.i("RetryInterceptor", "retry ${request.url} for the $retryNum time")
            response = try {
                chain.proceed(request)
            }catch (e: Exception){
                null
            }
        }
        return response ?: throw IOException("no response at all")
    }
}