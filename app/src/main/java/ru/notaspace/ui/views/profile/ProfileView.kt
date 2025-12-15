package ru.notaspace.ui.views.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.notaspace.ui.components.AppButton
import ru.notaspace.ui.viewmodel.AuthViewModel

/**
 * Экран профиля
 */
@Composable
fun ProfileView(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToTrash: () -> Unit = {},
    onNavigateToWorkspaces: () -> Unit = {}
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentUser != null) {
            Text(
                text = currentUser!!.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            if (currentUser!!.email != null) {
                Text(
                    text = currentUser!!.email!!,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }
        }
        
        AppButton(
            text = "Настройки",
            onClick = onNavigateToSettings,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        AppButton(
            text = "Уведомления",
            onClick = onNavigateToNotifications,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        AppButton(
            text = "Корзина",
            onClick = onNavigateToTrash,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        AppButton(
            text = "Workspaces",
            onClick = onNavigateToWorkspaces,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        AppButton(
            text = "Выйти",
            onClick = { authViewModel.logout() },
            isLoading = isLoading
        )
    }
}

