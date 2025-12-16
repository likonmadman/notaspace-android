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
 * ViewModel для списка страниц
 */
@HiltViewModel
class PagesListViewModel @Inject constructor(
    private val repository: PageRepository
) : ViewModel() {
    
    private val _pages = MutableStateFlow<List<Page>>(emptyList())
    val pages: StateFlow<List<Page>> = _pages.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    fun searchPages(query: String) {
        if (query.isBlank()) {
            _pages.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.searchPages(query)
                _pages.value = response.pages
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка поиска"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}



