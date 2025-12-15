package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Ответ от API при аутентификации
 */
data class AuthResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: User
)

