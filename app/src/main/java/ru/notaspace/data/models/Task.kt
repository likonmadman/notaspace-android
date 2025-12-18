package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Модель задачи
 */
data class Task(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("column_uuid")
    val columnUuid: String?,
    @SerializedName("assignee_uuid")
    val assigneeUuid: String?,
    @SerializedName("assignee")
    val assignee: User?,
    @SerializedName("due_date")
    val dueDate: String?,
    @SerializedName("priority_id")
    val priorityId: Int?,
    @SerializedName("priority")
    val priority: TaskPriority?,
    @SerializedName("tags")
    val tags: List<TaskTag>?,
    @SerializedName("is_favorite")
    val isFavorite: Boolean?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)

/**
 * Модель колонки задач
 */
data class TaskColumn(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)

/**
 * Модель приоритета задачи
 */
data class TaskPriority(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("color")
    val color: String?
)

/**
 * Модель тега задачи
 */
data class TaskTag(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("color")
    val color: String?
)

/**
 * Ответ со списком задач
 */
data class TaskListResponse(
    @SerializedName("data")
    val tasks: List<Task>,
    @SerializedName("meta")
    val meta: PaginationMeta?
)

/**
 * Метаданные пагинации
 */
data class PaginationMeta(
    @SerializedName("current_page")
    val currentPage: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("total")
    val total: Int?
)




