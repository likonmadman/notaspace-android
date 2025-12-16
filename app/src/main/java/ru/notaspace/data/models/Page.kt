package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Модель страницы
 */
data class Page(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String, // page, task, table, home
    @SerializedName("workspace_uuid")
    val workspaceUuid: String?,
    @SerializedName("content")
    val content: Any?,
    @SerializedName("blocks")
    val blocks: List<PageBlock>?,
    @SerializedName("is_favorite")
    val isFavorite: Boolean?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)

/**
 * Модель блока страницы
 */
data class PageBlock(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("content")
    val content: Any?,
    @SerializedName("order")
    val order: Int?
)



