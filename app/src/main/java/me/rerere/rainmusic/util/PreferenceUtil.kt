package me.rerere.rainmusic.util

import android.content.Context
import android.content.SharedPreferences
import me.rerere.rainmusic.AppContext

fun sharedPreferencesOf(
    name: String
): SharedPreferences = AppContext.instance.getSharedPreferences(name, Context.MODE_PRIVATE)