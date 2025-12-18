package ru.notaspace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.notaspace.data.models.Workspace
import ru.notaspace.data.repository.WorkspaceRepository
import javax.inject.Inject

/**
 * ViewModel для работы с workspace
 */
@HiltViewModel
class WorkspaceViewModel @Inject constructor(
    private val repository: WorkspaceRepository
) : ViewModel() {
    
    private val _workspaces = MutableStateFlow<List<Workspace>>(emptyList())
    val workspaces: StateFlow<List<Workspace>> = _workspaces.asStateFlow()
    
    private val _currentWorkspace = MutableStateFlow<Workspace?>(null)
    val currentWorkspace: StateFlow<Workspace?> = _currentWorkspace.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        loadWorkspaces()
    }
    
    fun loadWorkspaces() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _workspaces.value = repository.getWorkspaces()
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка загрузки workspace"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadWorkspace(uuid: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _currentWorkspace.value = repository.getWorkspace(uuid)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка загрузки workspace"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun createWorkspace(name: String, description: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val workspace = repository.createWorkspace(name, description)
                _workspaces.value = _workspaces.value + workspace
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка создания workspace"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}




