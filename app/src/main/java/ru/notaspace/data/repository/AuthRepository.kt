package ru.notaspace.data.repository

import ru.notaspace.data.local.TokenManager
import ru.notaspace.data.models.AuthResponse
import ru.notaspace.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Репозиторий для работы с аутентификацией
 */
@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    
    /**
     * Вход по email и паролю
     */
    suspend fun login(email: String, password: String): AuthResponse {
        val response = apiService.login(
            ru.notaspace.data.remote.LoginRequest(email, password)
        )
        tokenManager.saveToken(response.token)
        return response
    }
    
    /**
     * Вход по коду
     */
    suspend fun loginByCode(phone: String, code: String): AuthResponse {
        val response = apiService.loginByCode(
            ru.notaspace.data.remote.LoginByCodeRequest(phone, code)
        )
        tokenManager.saveToken(response.token)
        return response
    }
    
    /**
     * Регистрация
     */
    suspend fun signUp(name: String, email: String, password: String): AuthResponse {
        val response = apiService.signUp(
            ru.notaspace.data.remote.SignUpRequest(name, email, password)
        )
        tokenManager.saveToken(response.token)
        return response
    }
    
    /**
     * Выход
     */
    suspend fun logout() {
        try {
            apiService.logout()
        } finally {
            tokenManager.deleteToken()
        }
    }
    
    /**
     * Проверить наличие сохраненного токена
     */
    suspend fun hasToken(): Boolean {
        return tokenManager.getTokenSync() != null
    }
}

