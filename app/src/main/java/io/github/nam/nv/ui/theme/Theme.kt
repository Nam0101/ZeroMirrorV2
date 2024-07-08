package io.github.takusan23.zeromirror.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import io.github.nam.nv.ui.theme.md_theme_dark_background
import io.github.nam.nv.ui.theme.md_theme_dark_error
import io.github.nam.nv.ui.theme.md_theme_dark_errorContainer
import io.github.nam.nv.ui.theme.md_theme_dark_inverseOnSurface
import io.github.nam.nv.ui.theme.md_theme_dark_inversePrimary
import io.github.nam.nv.ui.theme.md_theme_dark_inverseSurface
import io.github.nam.nv.ui.theme.md_theme_dark_onBackground
import io.github.nam.nv.ui.theme.md_theme_dark_onError
import io.github.nam.nv.ui.theme.md_theme_dark_onErrorContainer
import io.github.nam.nv.ui.theme.md_theme_dark_onPrimary
import io.github.nam.nv.ui.theme.md_theme_dark_onPrimaryContainer
import io.github.nam.nv.ui.theme.md_theme_dark_onSecondary
import io.github.nam.nv.ui.theme.md_theme_dark_onSecondaryContainer
import io.github.nam.nv.ui.theme.md_theme_dark_onSurface
import io.github.nam.nv.ui.theme.md_theme_dark_onSurfaceVariant
import io.github.nam.nv.ui.theme.md_theme_dark_onTertiary
import io.github.nam.nv.ui.theme.md_theme_dark_onTertiaryContainer
import io.github.nam.nv.ui.theme.md_theme_dark_outline
import io.github.nam.nv.ui.theme.md_theme_dark_primary
import io.github.nam.nv.ui.theme.md_theme_dark_primaryContainer
import io.github.nam.nv.ui.theme.md_theme_dark_secondary
import io.github.nam.nv.ui.theme.md_theme_dark_secondaryContainer
import io.github.nam.nv.ui.theme.md_theme_dark_surface
import io.github.nam.nv.ui.theme.md_theme_dark_surfaceVariant
import io.github.nam.nv.ui.theme.md_theme_dark_tertiary
import io.github.nam.nv.ui.theme.md_theme_dark_tertiaryContainer
import io.github.nam.nv.ui.theme.md_theme_light_background
import io.github.nam.nv.ui.theme.md_theme_light_error
import io.github.nam.nv.ui.theme.md_theme_light_errorContainer
import io.github.nam.nv.ui.theme.md_theme_light_inverseOnSurface
import io.github.nam.nv.ui.theme.md_theme_light_inversePrimary
import io.github.nam.nv.ui.theme.md_theme_light_inverseSurface
import io.github.nam.nv.ui.theme.md_theme_light_onBackground
import io.github.nam.nv.ui.theme.md_theme_light_onError
import io.github.nam.nv.ui.theme.md_theme_light_onErrorContainer
import io.github.nam.nv.ui.theme.md_theme_light_onPrimary
import io.github.nam.nv.ui.theme.md_theme_light_onPrimaryContainer
import io.github.nam.nv.ui.theme.md_theme_light_onSecondary
import io.github.nam.nv.ui.theme.md_theme_light_onSecondaryContainer
import io.github.nam.nv.ui.theme.md_theme_light_onSurface
import io.github.nam.nv.ui.theme.md_theme_light_onSurfaceVariant
import io.github.nam.nv.ui.theme.md_theme_light_onTertiary
import io.github.nam.nv.ui.theme.md_theme_light_onTertiaryContainer
import io.github.nam.nv.ui.theme.md_theme_light_outline
import io.github.nam.nv.ui.theme.md_theme_light_primary
import io.github.nam.nv.ui.theme.md_theme_light_primaryContainer
import io.github.nam.nv.ui.theme.md_theme_light_secondary
import io.github.nam.nv.ui.theme.md_theme_light_secondaryContainer
import io.github.nam.nv.ui.theme.md_theme_light_surface
import io.github.nam.nv.ui.theme.md_theme_light_surfaceVariant
import io.github.nam.nv.ui.theme.md_theme_light_tertiary
import io.github.nam.nv.ui.theme.md_theme_light_tertiaryContainer

private val LightThemeColors = lightColorScheme(

    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
)
private val DarkThemeColors = darkColorScheme(

    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
)

/**
 * テーマ
 * アノテーションで警告を黙らせてるけどちゃんと動くようにしてあるのでおｋ
 *
 * @param isUseDynamicColor ダイナミックカラーを利用するか
 * @param darkTheme ダークモード
 */
@Composable
fun ZeroMirrorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isUseDynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    // Android 12以降で
    val colorScheme = if (darkTheme) {
        if (isUseDynamicColor) dynamicDarkColorScheme(context) else DarkThemeColors
    } else {
        if (isUseDynamicColor) dynamicLightColorScheme(context) else LightThemeColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}