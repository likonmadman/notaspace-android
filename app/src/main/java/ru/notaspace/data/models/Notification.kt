package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Модель уведомления
 */
data class Notification(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("data")
    val data: NotificationData?,
    @SerializedName("read_at")
    val readAt: String?,
    @SerializedName("created_at")
    val createdAt: String?
)

/**
 * Данные уведомления
 */
data class NotificationData(
    @SerializedName("message")
    val message: String?,
    @SerializedName("page_uuid")
    val pageUuid: String?,
    @SerializedName("task_uuid")
    val taskUuid: String?,
    @SerializedName("user")
    val user: User?
)

/**
 * Ответ со списком уведомлений
 */
data class NotificationListResponse(
    @SerializedName("data")
    val notifications: List<Notification>,
    @SerializedName("meta")
    val meta: PaginationMeta?
)

/**
 * Ответ с количеством непрочитанных
 */
data class UnreadCountResponse(
    @SerializedName("count")
    val count: Int
)




