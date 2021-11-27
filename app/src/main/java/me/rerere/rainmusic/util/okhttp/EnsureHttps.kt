package me.rerere.rainmusic.util.okhttp

import okhttp3.Interceptor
import okhttp3.Response

class EnsureHttpsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(if(request.isHttps) request else request.newBuilder().url(request.url.toString().https).build())
    }
}

val String.https: String
    get() = if(startsWith("https")) this else replace("http://","https://")