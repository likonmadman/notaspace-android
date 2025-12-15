package ru.notaspace.ui.views.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.notaspace.ui.viewmodel.PageViewModel
import ru.notaspace.ui.views.page.components.BlockEditor

/**
 * Экран просмотра/редактирования страницы
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageDetailView(
    pageUuid: String,
    onNavigateBack: () -> Unit,
    onNavigateToTable: (String) -> Unit = {},
    viewModel: PageViewModel = hiltViewModel()
) {
    val page by viewModel.currentPage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    LaunchedEffect(pageUuid) {
        viewModel.loadPage(pageUuid)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    if (page != null) {
                        var title by remember(page?.title) { mutableStateOf(page!!.title) }
                        TextField(
                            value = title,
                            onValueChange = { 
                                title = it
                                viewModel.updatePageTitle(pageUuid, it)
                            },
                            textStyle = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    } else {
                        Text("Загрузка...")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        floatingActionButton = {
            if (page != null && page!!.type == "page") {
                FloatingActionButton(
                    onClick = { /* TODO: Добавить новый блок */ }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить блок")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (errorMessage != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = errorMessage!!,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            } else if (page != null) {
                if (page!!.type == "page" && page!!.blocks != null && page!!.blocks.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(page!!.blocks) { index, block ->
                            BlockEditor(
                                block = block,
                                onContentChange = { newContent ->
                                    // TODO: Обновить содержимое блока через API
                                }
                            )
                        }
                    }
                } else if (page!!.type == "page") {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Страница пуста",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            TextButton(onClick = { /* TODO: Добавить первый блок */ }) {
                                Text("Добавить блок")
                            }
                        }
                    }
                } else if (page!!.type == "table") {
                    // Для таблиц перенаправляем на TableView
                    LaunchedEffect(pageUuid) {
                        onNavigateToTable(pageUuid)
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    // Для других типов страниц (task, home) показываем базовую информацию
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = page!!.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "Тип: ${page!!.type}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

