package ru.notaspace.data.remote

import retrofit2.http.*
import ru.notaspace.data.models.*

/**
 * Интерфейс для работы с API
 */
interface ApiService {
    
    // ========== AUTH ==========
    
    /**
     * Вход по email и паролю
     */
    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthResponse
    
    /**
     * Вход по коду
     */
    @POST("login-by-code")
    suspend fun loginByCode(@Body request: LoginByCodeRequest): AuthResponse
    
    /**
     * Регистрация
     */
    @POST("sign-up")
    suspend fun signUp(@Body request: SignUpRequest): AuthResponse
    
    /**
     * Выход
     */
    @POST("logout")
    suspend fun logout(): Unit
    
    // ========== WORKSPACES ==========
    
    /**
     * Получить список workspace
     */
    @GET("workspaces")
    suspend fun getWorkspaces(): List<Workspace>
    
    /**
     * Получить workspace по UUID
     */
    @GET("workspaces/{uuid}")
    suspend fun getWorkspace(@Path("uuid") uuid: String): Workspace
    
    /**
     * Создать workspace
     */
    @POST("workspaces")
    suspend fun createWorkspace(@Body request: CreateWorkspaceRequest): Workspace
    
    /**
     * Обновить workspace
     */
    @PUT("workspaces/{uuid}")
    suspend fun updateWorkspace(@Path("uuid") uuid: String, @Body request: UpdateWorkspaceRequest): Workspace
    
    /**
     * Удалить workspace
     */
    @DELETE("workspaces/{uuid}")
    suspend fun deleteWorkspace(@Path("uuid") uuid: String): Unit
    
    // ========== PAGES ==========
    
    /**
     * Создать страницу
     */
    @POST("page")
    suspend fun createPage(@Body request: CreatePageRequest): Page
    
    /**
     * Получить страницу по UUID
     */
    @GET("page/{uuid}")
    suspend fun getPage(@Path("uuid") uuid: String): Page
    
    /**
     * Обновить страницу
     */
    @PUT("page/{uuid}")
    suspend fun updatePage(@Path("uuid") uuid: String, @Body request: UpdatePageRequest): Page
    
    /**
     * Обновить заголовок страницы
     */
    @PUT("page/{uuid}/store-title")
    suspend fun updatePageTitle(@Path("uuid") uuid: String, @Body request: UpdateTitleRequest): Page
    
    /**
     * Удалить страницу
     */
    @DELETE("page/{uuid}")
    suspend fun deletePage(@Path("uuid") uuid: String): Unit
    
    /**
     * Переместить блоки
     */
    @POST("page/{uuid}/blocks/reorder")
    suspend fun reorderBlocks(@Path("uuid") uuid: String, @Body request: ReorderBlocksRequest): Unit
    
    /**
     * Переключить избранное
     */
    @POST("page/{uuid}/favorite")
    suspend fun toggleFavorite(@Path("uuid") uuid: String): Unit
    
    /**
     * Поиск страниц
     */
    @POST("search")
    suspend fun searchPages(@Body request: SearchRequest): SearchResponse
    
    // ========== TASKS ==========
    
    /**
     * Получить список задач страницы
     */
    @GET("page/{pageUuid}/tasks")
    suspend fun getTasks(
        @Path("pageUuid") pageUuid: String,
        @Query("column_uuid") columnUuid: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("q") query: String? = null
    ): TaskListResponse
    
    /**
     * Создать задачу
     */
    @POST("page/{pageUuid}/tasks")
    suspend fun createTask(@Path("pageUuid") pageUuid: String, @Body request: CreateTaskRequest): Task
    
    /**
     * Обновить задачу
     */
    @PUT("page/{pageUuid}/tasks/{taskUuid}")
    suspend fun updateTask(
        @Path("pageUuid") pageUuid: String,
        @Path("taskUuid") taskUuid: String,
        @Body request: UpdateTaskRequest
    ): Task
    
    /**
     * Удалить задачу
     */
    @DELETE("page/{pageUuid}/tasks/{taskUuid}")
    suspend fun deleteTask(@Path("pageUuid") pageUuid: String, @Path("taskUuid") taskUuid: String): Unit
    
    /**
     * Переместить задачи
     */
    @POST("page/{pageUuid}/tasks/reorder")
    suspend fun reorderTasks(@Path("pageUuid") pageUuid: String, @Body request: ReorderTasksRequest): Unit
    
    /**
     * Переключить избранное задачи
     */
    @POST("page/{pageUuid}/tasks/{taskUuid}/favorite")
    suspend fun toggleTaskFavorite(@Path("pageUuid") pageUuid: String, @Path("taskUuid") taskUuid: String): Unit
    
    // ========== TASK COLUMNS ==========
    
    /**
     * Получить колонки задач
     */
    @GET("page/{pageUuid}/columns")
    suspend fun getTaskColumns(@Path("pageUuid") pageUuid: String): List<TaskColumn>
    
    /**
     * Создать колонку
     */
    @POST("page/{pageUuid}/columns")
    suspend fun createTaskColumn(@Path("pageUuid") pageUuid: String, @Body request: CreateTaskColumnRequest): TaskColumn
    
    /**
     * Обновить колонку
     */
    @PUT("page/{pageUuid}/columns/{columnUuid}")
    suspend fun updateTaskColumn(
        @Path("pageUuid") pageUuid: String,
        @Path("columnUuid") columnUuid: String,
        @Body request: UpdateTaskColumnRequest
    ): TaskColumn
    
    /**
     * Удалить колонку
     */
    @DELETE("page/{pageUuid}/columns/{columnUuid}")
    suspend fun deleteTaskColumn(@Path("pageUuid") pageUuid: String, @Path("columnUuid") columnUuid: String): Unit
    
    /**
     * Переместить колонки
     */
    @POST("page/{pageUuid}/columns/reorder")
    suspend fun reorderTaskColumns(@Path("pageUuid") pageUuid: String, @Body request: ReorderColumnsRequest): Unit
    
    // ========== NOTIFICATIONS ==========
    
    /**
     * Получить уведомления
     */
    @GET("notifications")
    suspend fun getNotifications(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = null
    ): NotificationListResponse
    
    /**
     * Получить количество непрочитанных уведомлений
     */
    @GET("notifications/unread-count")
    suspend fun getUnreadCount(): UnreadCountResponse
    
    /**
     * Отметить уведомление как прочитанное
     */
    @POST("notifications/{id}/read")
    suspend fun markNotificationAsRead(@Path("id") id: Int): Unit
    
    /**
     * Отметить все уведомления как прочитанные
     */
    @POST("notifications/read-all")
    suspend fun markAllNotificationsAsRead(): Unit
    
    /**
     * Удалить уведомление
     */
    @DELETE("notifications/{id}")
    suspend fun deleteNotification(@Path("id") id: Int): Unit
    
    // ========== FILES ==========
    
    /**
     * Загрузить файл
     */
    @Multipart
    @POST("files")
    suspend fun uploadFile(
        @Part file: okhttp3.MultipartBody.Part,
        @Part("collection") collection: okhttp3.RequestBody? = null
    ): FileResource
    
    /**
     * Удалить файл
     */
    @DELETE("files/{uuid}")
    suspend fun deleteFile(@Path("uuid") uuid: String): Unit
    
    // ========== TRASH ==========
    
    /**
     * Получить корзину
     */
    @GET("trash")
    suspend fun getTrash(): TrashResponse
    
    /**
     * Восстановить из корзины
     */
    @POST("trash/{type}/{id}/restore")
    suspend fun restoreFromTrash(@Path("type") type: String, @Path("id") id: String): Unit
    
    /**
     * Удалить из корзины
     */
    @DELETE("trash/{type}/{id}")
    suspend fun deleteFromTrash(@Path("type") type: String, @Path("id") id: String): Unit
}

// ========== REQUEST MODELS ==========

/**
 * Запрос на вход
 */
data class LoginRequest(
    val email: String,
    val password: String
)

/**
 * Запрос на вход по коду
 */
data class LoginByCodeRequest(
    val phone: String,
    val code: String
)

/**
 * Запрос на регистрацию
 */
data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

/**
 * Запрос на создание workspace
 */
data class CreateWorkspaceRequest(
    val name: String,
    val description: String? = null
)

/**
 * Запрос на обновление workspace
 */
data class UpdateWorkspaceRequest(
    val name: String? = null,
    val description: String? = null
)

/**
 * Запрос на создание страницы
 */
data class CreatePageRequest(
    val workspace_uuid: String,
    val title: String,
    val type: String = "page"
)

/**
 * Запрос на обновление страницы
 */
data class UpdatePageRequest(
    val title: String? = null,
    val content: Any? = null
)

/**
 * Запрос на обновление заголовка
 */
data class UpdateTitleRequest(
    val title: String
)

/**
 * Запрос на перемещение блоков
 */
data class ReorderBlocksRequest(
    val blocks: List<BlockReorderItem>
)

data class BlockReorderItem(
    val uuid: String,
    val order: Int
)

/**
 * Запрос на поиск
 */
data class SearchRequest(
    val q: String
)

/**
 * Запрос на создание задачи
 */
data class CreateTaskRequest(
    val title: String,
    val column_uuid: String? = null,
    val assignee_uuid: String? = null,
    val description: String? = null,
    val due_date: String? = null,
    val priority_id: Int? = null,
    val tag_ids: List<Int>? = null
)

/**
 * Запрос на обновление задачи
 */
data class UpdateTaskRequest(
    val title: String? = null,
    val column_uuid: String? = null,
    val assignee_uuid: String? = null,
    val description: String? = null,
    val due_date: String? = null,
    val priority_id: Int? = null,
    val tag_ids: List<Int>? = null
)

/**
 * Запрос на перемещение задач
 */
data class ReorderTasksRequest(
    val tasks: List<TaskReorderItem>
)

data class TaskReorderItem(
    val uuid: String,
    val order: Int,
    val column_uuid: String? = null
)

/**
 * Запрос на создание колонки
 */
data class CreateTaskColumnRequest(
    val name: String,
    val order: Int? = null
)

/**
 * Запрос на обновление колонки
 */
data class UpdateTaskColumnRequest(
    val name: String? = null,
    val order: Int? = null
)

/**
 * Запрос на перемещение колонок
 */
data class ReorderColumnsRequest(
    val columns: List<ColumnReorderItem>
)

data class ColumnReorderItem(
    val uuid: String,
    val order: Int
)
