package me.rerere.rainmusic.util

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@ExperimentalAnimationApi
val defaultEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) = {
    slideInHorizontally(
        initialOffsetX = {
            it
        },
        animationSpec = tween()
    )
}

@ExperimentalAnimationApi
val defaultExitTransition : (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) = {
    slideOutHorizontally(
        targetOffsetX = {
            -it
        },
        animationSpec = tween()
    ) + fadeOut(
        animationSpec = tween()
    )
}

@ExperimentalAnimationApi
val defaultPopEnterTransition : (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) = {
    slideInHorizontally(
        initialOffsetX = {
            -it
        },
        animationSpec = tween()
    )
}

@ExperimentalAnimationApi
val defaultPopExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) = {
    slideOutHorizontally(
        targetOffsetX = {
            it
        },
        animationSpec = tween()
    )
}