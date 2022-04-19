package me.rerere.rainmusic.util

/**
 * 确保T是确定数值中的一员
 * 某些函数的参数可能是确定的几个值，使用此函数确保参数在确定范围内
 *
 * @receiver 要校验的值
 * @param condition 该值的几种可能性
 */
fun <T> T.requireOneOf(vararg condition: T) {
    require(condition.contains(this)) {
        "param must be one of: ${condition.joinToString(",")}"
    }
}