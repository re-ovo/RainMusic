package me.rerere.rainmusic.ui.local

import androidx.compose.runtime.compositionLocalOf
import me.rerere.rainmusic.model.UserData

val LocalUserData = compositionLocalOf<UserData> {
    UserData.VISITOR
}