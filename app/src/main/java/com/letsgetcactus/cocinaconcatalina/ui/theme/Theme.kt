package com.letsgetcactus.cocinaconcatalina.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = Red,
    onPrimary = Beis,
    secondary = DarkRed,
    onSecondary = Beis,
    tertiary = Red,
    onTertiary = Beis,

    background = DarkGrey,
    onBackground = Beis,
    surface = LightGrey,
    onSurface = Beis,


    )

private val LightColorScheme = lightColorScheme(
    primary = Red,
    onPrimary = Beis,
    secondary = DarkRed,
    onSecondary = Beis,
    tertiary = Black,
    onTertiary = Red,

    background = Beis,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    )

private val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = AbsoluteCutCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)

)

@Composable
fun CocinaConCatalinaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),

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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = AppShapes

    )
}