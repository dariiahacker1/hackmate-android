package com.teslasoft.hackerapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun HackerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) ColorScheme(
        primary = Color(0xFF1B8F75),
        onPrimary =  Color(0xFFD3D8D5),
        primaryContainer =  Color(0xFF0AE796),
        onPrimaryContainer =  Color(0xFF0AE796),
        inversePrimary =  Color(0xFFFFFFFF),
        secondary =  Color(0xFF125949),
        onSecondary =  Color(0xFFFFFFFF),
        secondaryContainer=  Color(0xFFFFFFFF),
        onSecondaryContainer=  Color(0xFFFFFFFF),
        tertiary=  Color(0xFFFFFFFF),
        onTertiary=  Color(0xFFFFFFFF),
        tertiaryContainer=  Color(0xFFFFFFFF),
        onTertiaryContainer=  Color(0xFFFFFFFF),
        background=  Color(0xFF131313),
        onBackground=  Color(0xFFFFFFFF),
        surface=  Color(0xFF333232),
        onSurface=  Color(0xFF131313),
        surfaceVariant=  Color(0xFFD3D8D5),
        onSurfaceVariant=  Color(0xFF888888),
        surfaceTint=  Color(0xFF8B9591),
        inverseSurface=  Color(0xFFFFFFFF),
        inverseOnSurface=  Color(0xFFDADADA),
        error= Color(0xDD155748),
        onError=  Color(0xFFCB1616),
        errorContainer=  Color(0xFFFFFFFF),
        onErrorContainer=  Color(0xFFFFFFFF),
        outline=  Color(0xFFCCFAE9),
        outlineVariant=  Color(0xFF232323),
        scrim= Color(0xFFFFFFFF),
    )

    else ColorScheme(
        primary = Color(0xFFFFFFFF),
        onPrimary =  Color(0xFFFFFFFF),
        primaryContainer =  Color(0xFFFFFFFF),
        onPrimaryContainer =  Color(0xFFFFFFFF),
        inversePrimary =  Color(0xFFFFFFFF),
        secondary =  Color(0xFFFFFFFF),
        onSecondary =  Color(0xFFFFFFFF),
        secondaryContainer=  Color(0xFFFFFFFF),
        onSecondaryContainer=  Color(0xFFFFFFFF),
        tertiary=  Color(0xFFFFFFFF),
        onTertiary=  Color(0xFFFFFFFF),
        tertiaryContainer=  Color(0xFFFFFFFF),
        onTertiaryContainer=  Color(0xFFFFFFFF),
        background=  Color(0xFFFFFFFF),
        onBackground=  Color(0xFFFFFFFF),
        surface=  Color(0xFFFFFFFF),
        onSurface=  Color(0xFFFFFFFF),
        surfaceVariant=  Color(0xFFFFFFFF),
        onSurfaceVariant=  Color(0xFFFFFFFF),
        surfaceTint=  Color(0xFFFFFFFF),
        inverseSurface=  Color(0xFFFFFFFF),
        inverseOnSurface=  Color(0xFFFFFFFF),
        error= Color(0xFFFFFFFF),
        onError=  Color(0xFFFFFFFF),
        errorContainer=  Color(0xFFFFFFFF),
        onErrorContainer=  Color(0xFFFFFFFF),
        outline=  Color(0xFFFFFFFF),
        outlineVariant=  Color(0xFFFFFFFF),
        scrim= Color(0xFFFFFFFF)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}