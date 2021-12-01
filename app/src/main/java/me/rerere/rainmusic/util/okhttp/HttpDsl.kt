package me.rerere.rainmusic.util.okhttp

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * 使用DSL创建一个http request
 */
fun OkHttpClient.request(builder: Request.Builder.() -> Unit): Response {
    val request = Request.Builder()
        .apply(builder)
        .build()
    return newCall(request).execute()
}

/**
 * 快速创建post form
 */
fun Request.Builder.post(vararg kv: Pair<String, String>) {
    post(
        FormBody.Builder().apply {
            kv.forEach {
                add(it.first, it.second)
            }
        }.build()
    )
}