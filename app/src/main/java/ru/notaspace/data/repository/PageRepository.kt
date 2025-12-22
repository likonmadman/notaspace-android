package ru.notaspace.data.repository

import ru.notaspace.data.models.Page
import ru.notaspace.data.models.SearchResponse
import ru.notaspace.data.remote.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы со страницами
 */
@Singleton
class PageRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun createPage(workspaceUuid: String, title: String, type: String = "page"): Page {
        return apiService.createPage(CreatePageRequest(workspaceUuid, title, type))
    }
    
    suspend fun getPage(uuid: String): Page = apiService.getPage(uuid)
    
    suspend fun updatePage(uuid: String, title: String? = null, content: Any? = null): Page {
        return apiService.updatePage(uuid, UpdatePageRequest(title, content))
    }
    
    suspend fun updatePageTitle(uuid: String, title: String): Page {
        return apiService.updatePageTitle(uuid, UpdateTitleRequest(title))
    }
    
    suspend fun deletePage(uuid: String) = apiService.deletePage(uuid)
    
    suspend fun reorderBlocks(uuid: String, blocks: List<BlockReorderItem>) {
        apiService.reorderBlocks(uuid, ReorderBlocksRequest(blocks))
    }
    
    suspend fun toggleFavorite(uuid: String) = apiService.toggleFavorite(uuid)
    
    suspend fun searchPages(query: String): SearchResponse {
        return apiService.searchPages(SearchRequest(query))
    }
}
