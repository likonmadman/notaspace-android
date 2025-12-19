package ru.notaspace.data.repository

import ru.notaspace.data.models.TrashResponse
import ru.notaspace.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы с корзиной
 */
@Singleton
class TrashRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTrash(): TrashResponse = apiService.getTrash()
    
    suspend fun restore(type: String, id: String) = apiService.restoreFromTrash(type, id)
    
    suspend fun delete(type: String, id: String) = apiService.deleteFromTrash(type, id)
}






