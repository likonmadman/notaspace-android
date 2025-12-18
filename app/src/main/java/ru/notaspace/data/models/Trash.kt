package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Ответ корзины
 */
data class TrashResponse(
    @SerializedName("pages")
    val pages: List<TrashItem>?,
    @SerializedName("workspaces")
    val workspaces: List<TrashItem>?
)

/**
 * Элемент корзины
 */
data class TrashItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String?,
    @SerializedName("deleted_at")
    val deletedAt: String?
)




