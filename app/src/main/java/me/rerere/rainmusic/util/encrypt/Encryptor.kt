package me.rerere.rainmusic.util.encrypt

import com.google.gson.JsonObject
import com.soywiz.krypto.AES
import com.soywiz.krypto.Padding
import com.soywiz.krypto.encoding.hex
import com.soywiz.krypto.encoding.toBase64
import com.soywiz.krypto.md5
import okhttp3.FormBody

private const val presetKey = "0CoJUm6Qyw8W8jud"
private const val iv = "0102030405060708"
private const val eapiKey = "e82ckenh8dichen8"

/**
 * weapi 接口加密
 *
 * @param data 原始post请求数据
 * @return 加密后的post body
 */
fun encryptWeAPI(
    data: Map<String, String> = emptyMap()
) : Map<String, String> {
    val rawJson = JsonObject().apply {
            data.forEach { (t, u) ->
                addProperty(t, u)
            }
        }.toString()

    val key = createRandomKey()

    return mapOf(
        "params" to AES.encryptAesCbc(
            data = AES.encryptAesCbc(
                data = rawJson.toByteArray(),
                key = presetKey.toByteArray(),
                iv = iv.toByteArray(),
                padding = Padding.PKCS7Padding
            ).toBase64().toByteArray(),
            key = key.toByteArray(),
            iv = iv.toByteArray(),
            padding = Padding.PKCS7Padding
        ).toBase64(),
        "encSecKey" to rsaEncrypt(
            key
        )
    )
}

fun encryptEApi(
    url: String,
    data: Map<String, String> = emptyMap()
) : Map<String, String> {
    val rawJson = JsonObject().apply {
        data.forEach { (t, u) ->
            addProperty(t, u)
        }
    }.toString()
    val message = "nobody" + url + "use" + rawJson + "md5forencrypt"
    val digest: String = message.toByteArray().md5().hex
    return mapOf(
        "params" to AES.encryptAesEcb(
            data = "$url-36cd479b6b5-$rawJson-36cd479b6b5-$digest".toByteArray(),
            key = eapiKey.toByteArray(),
            padding = Padding.PKCS7Padding
        ).hex
    )
}

fun createRandomKey() = StringBuilder().apply {
    repeat(16){
        append((('a'..'z') + ('A'..'Z') + ('0'..'9')).random())
    }
}.toString()

fun Map<String, String>.asPostBody() = FormBody.Builder()
    .apply {
        this@asPostBody.forEach { (k, v) ->
            add(k, v)
        }
    }
    .build()