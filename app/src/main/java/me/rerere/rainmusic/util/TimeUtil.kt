package me.rerere.rainmusic.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun currentTime() = System.currentTimeMillis()

@SuppressLint("SimpleDateFormat")
private val sdf = SimpleDateFormat("yyyy年MM月dd日")

@SuppressLint("SimpleDateFormat")
private val sdfDetail = SimpleDateFormat("yyyy年MM月dd日 HH:mm")

fun Long.format(
    detail: Boolean = false
): String = if (detail) sdfDetail.format(Date(this)) else sdf.format(Date(this))