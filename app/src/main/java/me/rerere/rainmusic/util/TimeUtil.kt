package me.rerere.rainmusic.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
private val sdf = SimpleDateFormat("yyyy年MM月dd日")

@SuppressLint("SimpleDateFormat")
private val sdfDetail = SimpleDateFormat("yyyy年MM月dd日 HH:mm")

/**
 * 获取当前时间戳
 *
 * @return 当前时间戳
 */
fun now() = System.currentTimeMillis()

/**
 * 将时间戳格式化成可读时间
 *
 * @receiver 时间戳
 * @param detail 是否显示"时分秒"
 * @return 可读时间
 */
fun Long.format(
    detail: Boolean = false
): String = if (detail) sdfDetail.format(Date(this)) else sdf.format(Date(this))

/**
 * 将"时间长度"格式化为可读时间
 */
fun Long.formatAsPlayerTime() : String {
    val minutes = String.format("%02d", this / 60_000L)
    val seconds = String.format("%02d", (this % 60_000L) / 1000L)
    return "$minutes:$seconds"
}