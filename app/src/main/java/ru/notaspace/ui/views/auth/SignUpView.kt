package ru.notaspace.ui.views.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.notaspace.ui.components.AppButton
import ru.notaspace.ui.components.AppTextField
import ru.notaspace.ui.viewmodel.AuthViewModel

/**
 * Экран регистрации
 */
@Composable
fun SignUpView(
    onNavigateToLogin: () -> Unit,
    onSignUpSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    LaunchedEffect(viewModel.isAuthenticated) {
        if (viewModel.isAuthenticated.value) {
            onSignUpSuccess()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        
        AppTextField(
            value = name,
            onValueChange = { name = it },
            label = "Имя",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        AppTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        AppTextField(
            value = password,
            onValueChange = { password = it },
            label = "Пароль",
            isPassword = true,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
        AppButton(
            text = "Зарегистрироваться",
            onClick = { viewModel.signUp(name, email, password) },
            isLoading = isLoading,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        TextButton(onClick = onNavigateToLogin) {
            Text("Уже есть аккаунт? Войти")
        }
    }
}
