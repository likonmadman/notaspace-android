package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Модель пользователя
 */
data class User(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("phone_formatted")
    val phoneFormatted: String?,
    @SerializedName("phone_code")
    val phoneCode: String?,
    @SerializedName("avatar")
    val avatar: FileResource?,
    @SerializedName("telegram_username")
    val telegramUsername: String?,
    @SerializedName("is_notify_email")
    val isNotifyEmail: Boolean?,
    @SerializedName("is_notify_telegram")
    val isNotifyTelegram: Boolean?
)

/**
 * Модель файла
 */
data class FileResource(
    @SerializedName("id")
    val id: String,
    @SerializedName("path")
    val path: String?,
    @SerializedName("file_name")
    val fileName: String?,
    @SerializedName("collection")
    val collection: String?,
    @SerializedName("original_url")
    val originalUrl: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("preview_url")
    val previewUrl: String?
)
