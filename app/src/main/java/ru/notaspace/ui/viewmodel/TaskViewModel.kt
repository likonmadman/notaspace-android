package ru.notaspace.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.notaspace.data.models.Task
import ru.notaspace.data.models.TaskColumn
import ru.notaspace.data.repository.TaskRepository
import javax.inject.Inject

/**
 * ViewModel для работы с задачами
 */
@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    
    private val _columns = MutableStateFlow<List<TaskColumn>>(emptyList())
    val columns: StateFlow<List<TaskColumn>> = _columns.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private var currentPageUuid: String? = null
    
    fun loadTasks(pageUuid: String, columnUuid: String? = null) {
        currentPageUuid = pageUuid
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.getTasks(pageUuid, columnUuid)
                _tasks.value = response.tasks
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка загрузки задач"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadColumns(pageUuid: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _columns.value = repository.getTaskColumns(pageUuid)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка загрузки колонок"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun createTask(
        pageUuid: String,
        title: String,
        columnUuid: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val task = repository.createTask(pageUuid, title, columnUuid)
                _tasks.value = _tasks.value + task
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Ошибка создания задачи"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}




