package ru.notaspace.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Менеджер для работы с токенами аутентификации
 */
@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")
    
    private val tokenKey = stringPreferencesKey("auth_token")
    
    /**
     * Получить токен
     */
    val token: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[tokenKey]
    }
    
    /**
     * Сохранить токен
     */
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }
    
    /**
     * Удалить токен
     */
    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }
    
    /**
     * Получить токен синхронно (для инициализации)
     */
    suspend fun getTokenSync(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[tokenKey]
        }.let { flow ->
            var token: String? = null
            flow.collect { token = it }
            token
        }
    }
}
