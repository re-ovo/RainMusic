package me.rerere.rainmusic

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppContext : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @JvmStatic
        lateinit var instance: AppContext
    }
}

fun sharedPreferencesOf(
    name: String
): SharedPreferences = AppContext.instance.getSharedPreferences(name, Context.MODE_PRIVATE)