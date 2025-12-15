package ru.notaspace.data.repository

import ru.notaspace.data.models.Task
import ru.notaspace.data.models.TaskColumn
import ru.notaspace.data.models.TaskListResponse
import ru.notaspace.data.remote.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы с задачами
 */
@Singleton
class TaskRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTasks(
        pageUuid: String,
        columnUuid: String? = null,
        limit: Int? = null,
        offset: Int? = null,
        query: String? = null
    ): TaskListResponse {
        return apiService.getTasks(pageUuid, columnUuid, limit, offset, query)
    }
    
    suspend fun createTask(
        pageUuid: String,
        title: String,
        columnUuid: String? = null,
        assigneeUuid: String? = null,
        description: String? = null,
        dueDate: String? = null,
        priorityId: Int? = null,
        tagIds: List<Int>? = null
    ): Task {
        return apiService.createTask(
            pageUuid,
            CreateTaskRequest(title, columnUuid, assigneeUuid, description, dueDate, priorityId, tagIds)
        )
    }
    
    suspend fun updateTask(
        pageUuid: String,
        taskUuid: String,
        title: String? = null,
        columnUuid: String? = null,
        assigneeUuid: String? = null,
        description: String? = null,
        dueDate: String? = null,
        priorityId: Int? = null,
        tagIds: List<Int>? = null
    ): Task {
        return apiService.updateTask(
            pageUuid,
            taskUuid,
            UpdateTaskRequest(title, columnUuid, assigneeUuid, description, dueDate, priorityId, tagIds)
        )
    }
    
    suspend fun deleteTask(pageUuid: String, taskUuid: String) {
        apiService.deleteTask(pageUuid, taskUuid)
    }
    
    suspend fun reorderTasks(pageUuid: String, tasks: List<TaskReorderItem>) {
        apiService.reorderTasks(pageUuid, ReorderTasksRequest(tasks))
    }
    
    suspend fun toggleFavorite(pageUuid: String, taskUuid: String) {
        apiService.toggleTaskFavorite(pageUuid, taskUuid)
    }
    
    // Task Columns
    suspend fun getTaskColumns(pageUuid: String): List<TaskColumn> {
        return apiService.getTaskColumns(pageUuid)
    }
    
    suspend fun createTaskColumn(pageUuid: String, name: String, order: Int? = null): TaskColumn {
        return apiService.createTaskColumn(pageUuid, CreateTaskColumnRequest(name, order))
    }
    
    suspend fun updateTaskColumn(pageUuid: String, columnUuid: String, name: String? = null, order: Int? = null): TaskColumn {
        return apiService.updateTaskColumn(pageUuid, columnUuid, UpdateTaskColumnRequest(name, order))
    }
    
    suspend fun deleteTaskColumn(pageUuid: String, columnUuid: String) {
        apiService.deleteTaskColumn(pageUuid, columnUuid)
    }
    
    suspend fun reorderTaskColumns(pageUuid: String, columns: List<ColumnReorderItem>) {
        apiService.reorderTaskColumns(pageUuid, ReorderColumnsRequest(columns))
    }
}


