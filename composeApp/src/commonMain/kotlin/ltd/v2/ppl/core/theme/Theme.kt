package ltd.v2.ppl.core.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val lightThemeColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = Blue400,
    onPrimaryContainer = Black2,

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = Teal300,
    onSecondaryContainer = Black2,

    tertiary = OrangeAccent,
    onTertiary = Black2,
    tertiaryContainer = YellowAccent,
    onTertiaryContainer = Black2,

    error = Error,
    onError = OnError,
    errorContainer = RedErrorDark,
    onErrorContainer = RedErrorLight,

    background = BackgroundLight,
    onBackground = OnBackground,

    surface = SurfaceLight,
    onSurface = OnSurface,
    surfaceVariant = Grey1,
    onSurfaceVariant = Grey2,

    outline = Grey2
)

val darkThemeColors = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = Black2,
    onPrimaryContainer = White,

    secondary = Black1,
    onSecondary = White,
    secondaryContainer = Teal300,
    onSecondaryContainer = Black2,

    tertiary = OrangeAccent,
    onTertiary = White,
    tertiaryContainer = YellowAccent,
    onTertiaryContainer = Black2,

    error = ErrorLight,
    onError = OnError,
    errorContainer = RedErrorDark,
    onErrorContainer = RedErrorLight,

    background = BackgroundDark,
    onBackground = OnBackground,

    surface = SurfaceDark,
    onSurface = OnSurface,
    surfaceVariant = Black1,
    onSurfaceVariant = Grey2,

    outline = Grey2
)



@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    val colors = if (!darkTheme) lightThemeColors else darkThemeColors
    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography(),
        shapes = Shapes,
        content = content,
    )
}

