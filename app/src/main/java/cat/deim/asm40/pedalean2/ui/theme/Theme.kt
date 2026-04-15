package cat.deim.asm40.pedalean2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PedaleanYellow,
    secondary = PedaleanGrey,
    tertiary = PedaleanGreyLight,
    onPrimary = PedaleanBlack
)

private val DarkColorScheme = darkColorScheme(
    primary = PedaleanYellowDark,
    secondary = PedaleanGrey,
    tertiary = PedaleanGreyLight,
    onPrimary = PedaleanBlack
)

@Composable
fun ASM40Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
