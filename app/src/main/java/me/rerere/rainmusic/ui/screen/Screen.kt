package me.rerere.rainmusic.ui.screen

import androidx.navigation.NavController

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Index : Screen("index")
    object Search : Screen("search")
    object Playlist : Screen("playlist")
    object Player : Screen("player")
    object DailySong: Screen("dailysong")
    object Test : Screen("test")

    inline fun navigate(
        navController: NavController,
        builder: NavigationBuilder.() -> Unit = {}
    ) {
        navController.navigate(NavigationBuilder(route).apply(builder).build())
    }
}

class NavigationBuilder(
    route: String
) {
    private var finalRoute: String = route
    private val query: MutableMap<String, String> = hashMapOf()

    fun addPath(path: String) {
        finalRoute += "/$path"
    }

    fun addQuery(key: String, value: String) {
        query += key to value
    }

    fun build(): String = if (query.isEmpty()) {
        finalRoute
    } else {
        "$finalRoute${
            query.entries.joinToString(
                separator = "&",
                prefix = "?"
            ) { "${it.key}=${it.value}" }
        }"
    }
}