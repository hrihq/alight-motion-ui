package com.hrihq.motionstudio.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.FloatingNavigationBar
import top.yukonga.miuix.kmp.basic.FloatingNavigationBarItem
import top.yukonga.miuix.kmp.theme.MiuixTheme

@Composable
fun MotionStudioApp() {
    var dark by rememberSaveable { mutableStateOf(true) }
    var tab by rememberSaveable { mutableIntStateOf(0) }
    var projects by remember { mutableStateOf(sampleProjects) }
    var showCreate by remember { mutableStateOf(false) }
    var info by remember { mutableStateOf<Pair<String, String>?>(null) }

    StudioTheme(dark = dark) {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            AnimatedContent(
                targetState = tab,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(220)) + slideInHorizontally { it / 8 }) togetherWith
                        fadeOut(animationSpec = tween(120))
                },
                label = "page",
            ) { page ->
                when (page) {
                    0 -> HomeScreen(
                        projects = projects,
                        onOpen = { info = it.title to "${it.duration} · ${it.meta}" },
                        onNew = { showCreate = true },
                    )

                    1 -> ProjectsScreen(
                        projects = projects,
                        onOpen = { info = it.title to "${it.duration} · ${it.meta}" },
                        onNew = { showCreate = true },
                    )

                    2 -> AssetsScreen(
                        onAsset = { name -> info = name to "Efek $name siap dipakai dalam proyek." },
                    )

                    else -> ProfileScreen(
                        dark = dark,
                        onDarkChange = { dark = it },
                        onAbout = { info = "Tentang" to "Motion Studio 1.0 · navbar oleh miuix." },
                    )
                }
            }

            // Navbar miuix: FloatingNavigationBar + FloatingNavigationBarItem.
            FloatingNavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp),
                color = MiuixTheme.colorScheme.surfaceContainer,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FloatingNavigationBarItem(
                        selected = tab == 0,
                        onClick = { tab = 0 },
                        icon = Icons.Filled.Home,
                        label = "Beranda",
                        modifier = Modifier.weight(1f),
                    )
                    FloatingNavigationBarItem(
                        selected = tab == 1,
                        onClick = { tab = 1 },
                        icon = Icons.Filled.Folder,
                        label = "Proyek",
                        modifier = Modifier.weight(1f),
                    )
                    Box(
                        Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Accent)
                            .clickable { showCreate = true },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Buat proyek",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp),
                        )
                    }
                    FloatingNavigationBarItem(
                        selected = tab == 2,
                        onClick = { tab = 2 },
                        icon = Icons.Filled.Layers,
                        label = "Aset",
                        modifier = Modifier.weight(1f),
                    )
                    FloatingNavigationBarItem(
                        selected = tab == 3,
                        onClick = { tab = 3 },
                        icon = Icons.Filled.Person,
                        label = "Profil",
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        if (showCreate) {
            CreateSheet(
                onDismiss = { showCreate = false },
                onCreate = { name ->
                    projects = listOf(Project(name, "00:00", "Baru dibuat", Accent)) + projects
                    showCreate = false
                    tab = 1
                },
            )
        }

        info?.let { (title, message) ->
            InfoDialog(title = title, message = message, onDismiss = { info = null })
        }
    }
}
