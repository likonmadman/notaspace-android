package ru.notaspace

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Главный класс приложения
 * Инициализирует Hilt для Dependency Injection
 */
@HiltAndroidApp
class NotaSpaceApplication : Application()
