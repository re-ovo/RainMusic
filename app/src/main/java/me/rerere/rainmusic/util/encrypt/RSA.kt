package me.rerere.rainmusic.util.encrypt

import java.math.BigInteger

private const val pubKey = "010001"
private const val modulus =
    "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7"

private fun zFill(str: String): String {
    var str = str
    val strBuilder = StringBuilder(str)
    while (strBuilder.length < 256) {
        strBuilder.insert(0, "0")
    }
    str = strBuilder.toString()
    return str
}

fun rsaEncrypt(text: String): String {
    val text = StringBuffer(text).reverse().toString()
    val biText = BigInteger(strToHex(text), 16)
    val biEx = BigInteger(pubKey, 16)
    val biMod = BigInteger(modulus, 16)
    val biRet: BigInteger = biText.modPow(biEx, biMod)
    return zFill(biRet.toString(16))
}

private fun strToHex(s: String): String {
    val str = java.lang.StringBuilder()
    for (element in s) {
        val ch = element.code
        val s4 = Integer.toHexString(ch)
        str.append(s4)
    }
    return str.toString()
}