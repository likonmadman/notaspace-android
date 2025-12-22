package ru.notaspace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.notaspace.data.models.Notification
import ru.notaspace.data.repository.NotificationRepository
import javax.inject.Inject

/**
 * ViewModel для работы с уведомлениями
 */
@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {
    
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()
    
    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        loadNotifications()
        loadUnreadCount()
    }
    
    fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.getNotifications()
                _notifications.value = response.notifications
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка загрузки уведомлений"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadUnreadCount() {
        viewModelScope.launch {
            try {
                val response = repository.getUnreadCount()
                _unreadCount.value = response.count
            } catch (e: Exception) {
                // Игнорируем ошибки при загрузке счетчика
            }
        }
    }
    
    fun markAsRead(id: Int) {
        viewModelScope.launch {
            try {
                repository.markAsRead(id)
                _notifications.value = _notifications.value.map { notification ->
                    if (notification.id == id) {
                        notification.copy(readAt = "now") // TODO: использовать реальное время
                    } else {
                        notification
                    }
                }
                loadUnreadCount()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка обновления уведомления"
            }
        }
    }
    
    fun markAllAsRead() {
        viewModelScope.launch {
            try {
                repository.markAllAsRead()
                loadNotifications()
                loadUnreadCount()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка обновления уведомлений"
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}
