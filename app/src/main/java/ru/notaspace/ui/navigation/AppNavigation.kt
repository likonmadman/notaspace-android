package ru.notaspace.ui.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.notaspace.ui.views.auth.LoginView
import ru.notaspace.ui.views.auth.SignUpView
import ru.notaspace.ui.views.home.HomeView
import ru.notaspace.ui.views.notifications.NotificationsView
import ru.notaspace.ui.views.trash.TrashView
import ru.notaspace.ui.views.workspaces.WorkspacesView
import ru.notaspace.ui.views.page.PageDetailView
import ru.notaspace.ui.views.settings.SettingsView
import ru.notaspace.ui.views.table.TableView
import ru.notaspace.ui.viewmodel.AuthViewModel

/**
 * Навигация приложения
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) "home" else "login"
    ) {
        composable("login") {
            LoginView(
                onNavigateToSignUp = {
                    navController.navigate("signup")
                },
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        
        composable("signup") {
            SignUpView(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onSignUpSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        
        composable("home") {
            HomeView(
                authViewModel = authViewModel,
                onNavigateToPage = { pageUuid ->
                    navController.navigate("page/$pageUuid")
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                },
                onNavigateToNotifications = {
                    navController.navigate("notifications")
                },
                onNavigateToTrash = {
                    navController.navigate("trash")
                },
                onNavigateToWorkspaces = {
                    navController.navigate("workspaces")
                }
            )
        }
        
        composable(
            route = "page/{pageUuid}",
            arguments = listOf(navArgument("pageUuid") { type = NavType.StringType })
        ) { backStackEntry ->
            val pageUuid = backStackEntry.arguments?.getString("pageUuid") ?: ""
            // Определяем тип страницы и показываем соответствующий экран
            // Для упрощения всегда показываем PageDetailView, который сам определит тип
            PageDetailView(
                pageUuid = pageUuid,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToTable = { tableUuid ->
                    navController.navigate("table/$tableUuid")
                }
            )
        }
        
        composable(
            route = "table/{pageUuid}",
            arguments = listOf(navArgument("pageUuid") { type = NavType.StringType })
        ) { backStackEntry ->
            val pageUuid = backStackEntry.arguments?.getString("pageUuid") ?: ""
            TableView(
                pageUuid = pageUuid,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("notifications") {
            NotificationsView(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("trash") {
            TrashView(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("workspaces") {
            WorkspacesView(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToWorkspace = { workspaceUuid ->
                    // TODO: навигация к workspace
                }
            )
        }
        
        composable("settings") {
            SettingsView(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
    
    // Обработка выхода - переход на экран входа
    LaunchedEffect(isAuthenticated) {
        if (!isAuthenticated && navController.currentDestination?.route != "login") {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }
}

