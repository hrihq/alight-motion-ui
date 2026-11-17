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

// Warna dari APK asli (REA decode-android-resources).
val BgLight = Color(0xFFF6F6F6)
val BgDark = Color(0xFF0F1418)
val PanelDark = Color(0xFF1F233A)

@Composable
fun StudioTheme(dark: Boolean, content: @Composable () -> Unit) {
    val controller = remember(dark) {
        ThemeController(
            colorSchemeMode = if (dark) ColorSchemeMode.Dark else ColorSchemeMode.Light,
            keyColor = Accent,
        )
    }
    val scheme = if (dark) {
        darkColorScheme(
            primary = Accent,
            secondary = AccentSoft,
            background = BgDark,
            surface = PanelDark,
        )
    } else {
        lightColorScheme(
            primary = Accent,
            secondary = AccentSoft,
            background = BgLight,
        )
    }
    MaterialTheme(colorScheme = scheme) {
        MiuixTheme(controller = controller, content = content)
    }
}
