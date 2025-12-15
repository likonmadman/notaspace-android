package ru.notaspace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.notaspace.data.models.User
import ru.notaspace.data.repository.AuthRepository
import javax.inject.Inject

/**
 * ViewModel для аутентификации
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        // Проверяем наличие сохраненного токена при запуске
        viewModelScope.launch {
            if (authRepository.hasToken()) {
                _isAuthenticated.value = true
                // TODO: Загрузить данные пользователя
            }
        }
    }
    
    /**
     * Вход по email и паролю
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val response = authRepository.login(email, password)
                _currentUser.value = response.user
                _isAuthenticated.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка входа"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Вход по коду
     */
    fun loginByCode(phone: String, code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val response = authRepository.loginByCode(phone, code)
                _currentUser.value = response.user
                _isAuthenticated.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка входа"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Регистрация
     */
    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val response = authRepository.signUp(name, email, password)
                _currentUser.value = response.user
                _isAuthenticated.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка регистрации"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Выход
     */
    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                authRepository.logout()
                _currentUser.value = null
                _isAuthenticated.value = false
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка выхода"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Очистить сообщение об ошибке
     */
    fun clearError() {
        _errorMessage.value = null
    }
}

