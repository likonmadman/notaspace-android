package ru.notaspace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.notaspace.data.models.Page
import ru.notaspace.data.repository.PageRepository
import javax.inject.Inject

/**
 * ViewModel для работы со страницами
 */
@HiltViewModel
class PageViewModel @Inject constructor(
    private val repository: PageRepository
) : ViewModel() {
    
    private val _currentPage = MutableStateFlow<Page?>(null)
    val currentPage: StateFlow<Page?> = _currentPage.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    fun loadPage(uuid: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _currentPage.value = repository.getPage(uuid)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка загрузки страницы"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun updatePageTitle(uuid: String, title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _currentPage.value = repository.updatePageTitle(uuid, title)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка обновления страницы"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun toggleFavorite(uuid: String) {
        viewModelScope.launch {
            try {
                repository.toggleFavorite(uuid)
                _currentPage.value?.let { page ->
                    _currentPage.value = page.copy(isFavorite = !(page.isFavorite ?: false))
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка обновления избранного"
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}




