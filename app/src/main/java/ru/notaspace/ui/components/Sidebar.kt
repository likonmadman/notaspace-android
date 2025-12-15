package ru.notaspace.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Sidebar навигация
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSidebar(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onNavigateToHome: () -> Unit = {},
    onNavigateToPages: () -> Unit = {},
    onNavigateToTasks: () -> Unit = {},
    onNavigateToWorkspaces: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToTrash: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    currentRoute: String = "",
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(if (isOpen) DrawerValue.Open else DrawerValue.Closed)
    
    LaunchedEffect(isOpen) {
        if (isOpen) {
            drawerState.open()
        } else {
            drawerState.close()
        }
    }
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        onDismissRequest = onDismiss,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
                    .padding(16.dp)
            ) {
                // Заголовок
                Text(
                    text = "NotaSpace",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // Навигационные элементы
                SidebarItem(
                    title = "Главная",
                    icon = Icons.Default.Home,
                    onClick = {
                        onNavigateToHome()
                        onDismiss()
                    },
                    isSelected = currentRoute == "home"
                )
                
                SidebarItem(
                    title = "Страницы",
                    icon = Icons.Default.Description,
                    onClick = {
                        onNavigateToPages()
                        onDismiss()
                    },
                    isSelected = currentRoute.startsWith("pages")
                )
                
                SidebarItem(
                    title = "Задачи",
                    icon = Icons.Default.CheckCircle,
                    onClick = {
                        onNavigateToTasks()
                        onDismiss()
                    },
                    isSelected = currentRoute.startsWith("tasks")
                )
                
                SidebarItem(
                    title = "Workspaces",
                    icon = Icons.Default.Folder,
                    onClick = {
                        onNavigateToWorkspaces()
                        onDismiss()
                    },
                    isSelected = currentRoute == "workspaces"
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SidebarItem(
                    title = "Уведомления",
                    icon = Icons.Default.Notifications,
                    onClick = {
                        onNavigateToNotifications()
                        onDismiss()
                    },
                    isSelected = currentRoute == "notifications"
                )
                
                SidebarItem(
                    title = "Корзина",
                    icon = Icons.Default.Delete,
                    onClick = {
                        onNavigateToTrash()
                        onDismiss()
                    },
                    isSelected = currentRoute == "trash"
                )
                
                SidebarItem(
                    title = "Настройки",
                    icon = Icons.Default.Settings,
                    onClick = {
                        onNavigateToSettings()
                        onDismiss()
                    },
                    isSelected = currentRoute == "settings"
                )
            }
        }
    ) {
        content()
    }
}

@Composable
fun SidebarItem(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    isSelected: Boolean = false
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        Color.Transparent
    }
    
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .padding(12.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

