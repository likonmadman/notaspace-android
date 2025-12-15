package ru.notaspace.ui.views.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.notaspace.ui.components.AppSidebar
import ru.notaspace.ui.views.pages.PagesView
import ru.notaspace.ui.views.profile.ProfileView
import ru.notaspace.ui.views.tasks.TasksView
import ru.notaspace.ui.viewmodel.AuthViewModel

/**
 * Главный экран с табами
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToPage: (String) -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToTrash: () -> Unit = {},
    onNavigateToWorkspaces: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var sidebarOpen by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NotaSpace") },
                navigationIcon = {
                    IconButton(onClick = { sidebarOpen = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Меню")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = "Страницы") },
                    label = { Text("Страницы") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Задачи") },
                    label = { Text("Задачи") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Профиль") },
                    label = { Text("Профиль") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { paddingValues ->
        AppSidebar(
            isOpen = sidebarOpen,
            onDismiss = { sidebarOpen = false },
            onNavigateToHome = { selectedTab = 0 },
            onNavigateToPages = { selectedTab = 0 },
            onNavigateToTasks = { selectedTab = 1 },
            onNavigateToWorkspaces = onNavigateToWorkspaces,
            onNavigateToNotifications = onNavigateToNotifications,
            onNavigateToTrash = onNavigateToTrash,
            onNavigateToSettings = onNavigateToSettings
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedTab) {
                    0 -> PagesView(onPageClick = onNavigateToPage)
                    1 -> TasksView()
                    2 -> ProfileView(
                        authViewModel = authViewModel,
                        onNavigateToSettings = onNavigateToSettings,
                        onNavigateToNotifications = onNavigateToNotifications,
                        onNavigateToTrash = onNavigateToTrash,
                        onNavigateToWorkspaces = onNavigateToWorkspaces
                    )
                }
            }
        }
    }
}

