package me.rerere.rainmusic.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material3.ColorScheme

fun ColorScheme.toMd2Colors(light: Boolean): Colors = Colors(
    primary = this.primary,
    onPrimary = this.onPrimary,
    primaryVariant = this.primary,
    secondary = this.secondary,
    onSecondary = this.onSecondary,
    secondaryVariant = this.secondary,
    background = this.background,
    onBackground = this.onBackground,
    surface = this.surface,
    onSurface = this.onSurface,
    error = this.error,
    onError = this.onError,
    isLight = light
)