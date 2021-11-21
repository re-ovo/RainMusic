package me.rerere.rainmusic.ui.screen

sealed class Screen(val route: String){
    object Login: Screen("login")
    object Index: Screen("index")
}