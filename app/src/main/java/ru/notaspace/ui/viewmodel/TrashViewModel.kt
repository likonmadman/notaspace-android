package ru.notaspace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.notaspace.data.models.TrashItem
import ru.notaspace.data.repository.TrashRepository
import javax.inject.Inject

/**
 * ViewModel для работы с корзиной
 */
@HiltViewModel
class TrashViewModel @Inject constructor(
    private val repository: TrashRepository
) : ViewModel() {
    
    private val _trashItems = MutableStateFlow<List<TrashItem>>(emptyList())
    val trashItems: StateFlow<List<TrashItem>> = _trashItems.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        loadTrash()
    }
    
    fun loadTrash() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.getTrash()
                val items = mutableListOf<TrashItem>()
                response.pages?.let { items.addAll(it) }
                response.workspaces?.let { items.addAll(it) }
                _trashItems.value = items
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка загрузки корзины"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun restore(type: String, id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                repository.restore(type, id)
                loadTrash()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка восстановления"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun delete(type: String, id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                repository.delete(type, id)
                loadTrash()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка удаления"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}






