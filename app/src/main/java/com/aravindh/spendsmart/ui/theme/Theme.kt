package com.aravindh.spendsmart.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = cyan900,
    secondary = black,
    tertiary = white,
    onTertiaryContainer = black,
    background = black,
    outline = white,
    onPrimaryContainer = white,
    surface = warmGray700,
    onPrimary = white,
    onSecondary = white,
    surfaceVariant = black,
    onTertiary = cyan900,
    onSurface = warmGray700,
    inverseSurface = gray,
    onSurfaceVariant = white,
    onBackground = warmGray700,
)

private val LightColorScheme = lightColorScheme(
    primary = cyan900,
    outline = black,
    secondary = warmGray700,
    tertiary = black,
    background = warmGray100,
    onTertiaryContainer = warmGray100,
    onPrimaryContainer = white,
    inverseSurface = white,
    surface = warmGray200,
    surfaceVariant = cyan900,
    onPrimary = white,
    onSecondary = white,
    onTertiary = cyan900,
    onSurface = cyan300,
    onSurfaceVariant = white,
    onBackground = warmGray700,

    )

@Composable
fun SpendSmartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}