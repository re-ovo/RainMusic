package me.rerere.rainmusic.ui.screen

import androidx.navigation.NavController

sealed class Screen(val route: String){
    object Login: Screen("login")
    object Index: Screen("index")
    object Search: Screen("search")
    object Playlist: Screen("playlist")
    object Player: Screen("player")

    inline fun navigate(
        navController: NavController,
        builder: (String) -> String = {
            it
        }
    ) {
        navController.navigate(builder(route))
    }
}