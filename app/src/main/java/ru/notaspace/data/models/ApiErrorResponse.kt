package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Структура ошибки от сервера
 */
data class ApiErrorResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("error")
    val error: String?
)

