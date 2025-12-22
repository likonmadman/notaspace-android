package ru.notaspace.data.remote

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.notaspace.data.local.TokenManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Интерсептор для добавления токена аутентификации в запросы
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Получаем токен синхронно для добавления в заголовок
        val token = runBlocking {
            tokenManager.token.first()
        }
        
        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build()
        } else {
            originalRequest.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build()
        }
        
        return chain.proceed(newRequest)
    }
}
