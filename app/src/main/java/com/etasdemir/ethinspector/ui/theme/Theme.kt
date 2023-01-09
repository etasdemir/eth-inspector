package com.etasdemir.ethinspector.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFEBDB),
    secondary = Color(0xFFFFAB91),
    tertiary = Color(0xFFC97B63),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFF5F5F5),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFEBDB),
    secondary = Color(0xFFFFAB91),
    tertiary = Color(0xFFC97B63),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFF5F5F5),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),


    /* Other default colors to override
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun EthInspectorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.secondary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}