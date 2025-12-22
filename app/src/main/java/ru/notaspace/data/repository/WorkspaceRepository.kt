package ru.notaspace.data.repository

import ru.notaspace.data.models.Workspace
import ru.notaspace.data.remote.ApiService
import ru.notaspace.data.remote.CreateWorkspaceRequest
import ru.notaspace.data.remote.UpdateWorkspaceRequest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы с workspace
 */
@Singleton
class WorkspaceRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getWorkspaces(): List<Workspace> = apiService.getWorkspaces()
    suspend fun getWorkspace(uuid: String): Workspace = apiService.getWorkspace(uuid)
    suspend fun createWorkspace(name: String, description: String? = null): Workspace {
        return apiService.createWorkspace(CreateWorkspaceRequest(name, description))
    }
    suspend fun updateWorkspace(uuid: String, name: String? = null, description: String? = null): Workspace {
        return apiService.updateWorkspace(uuid, UpdateWorkspaceRequest(name, description))
    }
    suspend fun deleteWorkspace(uuid: String) = apiService.deleteWorkspace(uuid)
}
