package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Модель workspace
 */
data class Workspace(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)






