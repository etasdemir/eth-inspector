package com.etasdemir.ethinspector.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.etasdemir.ethinspector.data.domain_model.AvailableThemes
import com.etasdemir.ethinspector.ui.shared.SharedAccountViewModel

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF604235),
    secondary = Color(0xFFB8735D),
    tertiary = Color(0xFFC97B63),
    background = Color(0xFF272727),
    surface = Color(0xFF333333),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    error = Color.Red,
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFEBDB),
    secondary = Color(0xFFFFAB91),
    tertiary = Color(0xFFC97B63),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFF5F5F5),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    error = Color.Red,
)

val LocalTheme = compositionLocalOf { AvailableThemes.Dark }

@Composable
fun EthInspectorTheme(
    accountViewModel: SharedAccountViewModel,
    content: @Composable () -> Unit
) {
    val userState by accountViewModel.userState.collectAsState(initial = null)

    LaunchedEffect(key1 = "fetch_user") {
        accountViewModel.getUser()
    }

    if (userState == null) {
        return
    }

    val theme = if (userState!!.useSystemTheme) {
        if (isSystemInDarkTheme()) AvailableThemes.Dark else AvailableThemes.Light
    } else {
        userState!!.theme
    }
    val isDarkTheme = theme == AvailableThemes.Dark
    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.secondary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = isDarkTheme
        }
    }

    CompositionLocalProvider(LocalTheme provides theme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}