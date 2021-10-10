package me.rerere.rainmusic.util.okhttp

import android.webkit.WebSettings
import me.rerere.rainmusic.AppContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class UserAgentInterceptor(
    private val userAgent: String = WebSettings.getDefaultUserAgent(
        AppContext.instance
    )
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val userAgentRequest: Request = chain.request()
            .newBuilder()
            .header("User-Agent", userAgent)
            .build()
        return chain.proceed(userAgentRequest)
    }
}