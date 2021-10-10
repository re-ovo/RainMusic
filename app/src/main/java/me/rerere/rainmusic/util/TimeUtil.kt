package me.rerere.rainmusic.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun currentTime() = System.currentTimeMillis()

@SuppressLint("SimpleDateFormat")
private val sdf = SimpleDateFormat("yyyy-MM-dd")

@SuppressLint("SimpleDateFormat")
private val sdfDetail = SimpleDateFormat("yyy-MM-dd HH:mm")

fun Long.format(
    detail: Boolean = false
): String = if (detail) sdfDetail.format(Date(this)) else sdf.format(Date(this))