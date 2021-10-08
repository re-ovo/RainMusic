package me.rerere.rainmusic.util.encrypt

import com.google.gson.JsonObject
import com.soywiz.krypto.AES
import com.soywiz.krypto.Padding
import com.soywiz.krypto.encoding.toBase64

private const val presetKey = "0CoJUm6Qyw8W8jud"
private const val iv = "0102030405060708"

/**
 * weapi 接口加密
 *
 * @param data 原始post请求数据
 * @return 加密后的post body
 */
fun encryptWeAPI(
    data: Map<String, String>
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

fun createRandomKey() = StringBuilder().apply {
    repeat(16){
        append((('a'..'z') + ('A'..'Z') + ('0'..'9')).random())
    }
}.toString()