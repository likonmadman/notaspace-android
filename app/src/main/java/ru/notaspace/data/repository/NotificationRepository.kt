package ru.notaspace.data.repository

import ru.notaspace.data.models.NotificationListResponse
import ru.notaspace.data.models.UnreadCountResponse
import ru.notaspace.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы с уведомлениями
 */
@Singleton
class NotificationRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getNotifications(page: Int? = null, perPage: Int? = null): NotificationListResponse {
        return apiService.getNotifications(page, perPage)
    }
    
    suspend fun getUnreadCount(): UnreadCountResponse = apiService.getUnreadCount()
    
    suspend fun markAsRead(id: Int) = apiService.markNotificationAsRead(id)
    
    suspend fun markAllAsRead() = apiService.markAllNotificationsAsRead()
    
    suspend fun deleteNotification(id: Int) = apiService.deleteNotification(id)
}




