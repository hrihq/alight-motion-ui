package com.hrihq.motionstudio.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import top.yukonga.miuix.kmp.theme.ColorSchemeMode
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.ThemeController

val Accent = Color(0xFF8B6CF6)
val AccentSoft = Color(0xFF4FC3F7)

@Composable
fun StudioTheme(dark: Boolean, content: @Composable () -> Unit) {
    val controller = remember(dark) {
        ThemeController(
            colorSchemeMode = if (dark) ColorSchemeMode.Dark else ColorSchemeMode.Light,
            keyColor = Accent,
        )
    }
    val scheme = if (dark) {
        darkColorScheme(primary = Accent, secondary = AccentSoft)
    } else {
        lightColorScheme(primary = Accent, secondary = AccentSoft)
    }
    MaterialTheme(colorScheme = scheme) {
        MiuixTheme(controller = controller, content = content)
    }
}
