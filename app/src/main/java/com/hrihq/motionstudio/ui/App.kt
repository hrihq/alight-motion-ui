package com.hrihq.motionstudio.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.FloatingNavigationBar
import top.yukonga.miuix.kmp.theme.MiuixTheme

private val tabs = listOf(
    "Home" to Icons.Filled.Home,
    "Creators" to Icons.Filled.Star,
    "Tutorials" to Icons.Filled.School,
    "Projects" to Icons.Filled.Folder,
    "Your Templates" to Icons.Filled.Description,
)

@Composable
fun MotionStudioApp() {
    var dark by rememberSaveable { mutableStateOf(true) }
    var tab by rememberSaveable { mutableIntStateOf(0) }
    var projects by remember { mutableStateOf(sampleProjects) }
    var showCreate by remember { mutableStateOf(false) }
    var info by remember { mutableStateOf<Pair<String, String>?>(null) }

    StudioTheme(dark = dark) {
        Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            Column(Modifier.fillMaxSize()) {
                // Top bar 57 dp — sesuai activity_home_screen.xml dari APK asli.
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(57.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { dark = !dark }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Ganti tema")
                    }
                    Text(
                        "Motion Studio",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                    )
                    IconButton(onClick = { showCreate = true }) {
                        Icon(Icons.Filled.Add, contentDescription = "Buat proyek")
                    }
                    Box(
                        Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Accent),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("MS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                Box(Modifier.weight(1f)) {
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

                            1 -> SimpleListScreen(
                                title = "Creators",
                                subtitle = "Ranking pembuat template",
                                items = listOf("Top Creator", "Rising Star", "Featured Artist", "Community Pick"),
                                onNew = { showCreate = true },
                            )

                            2 -> SimpleListScreen(
                                title = "Tutorials",
                                subtitle = "Belajar animasi langkah demi langkah",
                                items = listOf("Dash Animator", "Shape Layers", "Text on Path", "Export Tips"),
                                onNew = { showCreate = true },
                            )

                            3 -> ProjectsScreen(
                                projects = projects,
                                onOpen = { info = it.title to "${it.duration} · ${it.meta}" },
                                onNew = { showCreate = true },
                            )

                            else -> SimpleListScreen(
                                title = "Your Templates",
                                subtitle = "Template tersimpan",
                                items = listOf("Neon Titles", "Logo Reveal", "Lower Third", "Kinetic Text"),
                                onNew = { showCreate = true },
                            )
                        }
                    }

                    // Navbar miuix — 5 tab sesuai bukti REA, area 72 dp.
                    FloatingNavigationBar(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(72.dp),
                        color = MiuixTheme.colorScheme.surfaceContainer,
                        horizontalOutSidePadding = 8.dp,
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            tabs.forEachIndexed { index, (label, icon) ->
                                NavTab(
                                    selected = tab == index,
                                    onClick = { tab = index },
                                    icon = icon,
                                    label = label,
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showCreate) {
            CreateSheet(
                onDismiss = { showCreate = false },
                onCreate = { name ->
                    projects = listOf(Project(name, "00:00", "Baru dibuat", Accent)) + projects
                    showCreate = false
                    tab = 3
                },
            )
        }

        info?.let { (title, message) ->
            InfoDialog(title = title, message = message, onDismiss = { info = null })
        }
    }
}

@Composable
private fun RowScope.NavTab(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
) {
    val tint = if (selected) Accent else MaterialTheme.colorScheme.onSurfaceVariant
    Column(
        Modifier
            .weight(1f)
            .selectable(selected = selected, onClick = onClick, role = Role.Tab),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(22.dp))
        Spacer(Modifier.height(2.dp))
        Text(
            label,
            color = tint,
            fontSize = 9.sp,
            maxLines = 1,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}
