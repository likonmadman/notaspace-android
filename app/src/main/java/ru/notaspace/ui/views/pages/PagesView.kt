package ru.notaspace.ui.views.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.notaspace.ui.viewmodel.PagesListViewModel

/**
 * Экран со списком страниц
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagesView(
    onPageClick: (String) -> Unit = {},
    viewModel: PagesListViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val pages by viewModel.pages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 2) {
            viewModel.searchPages(searchQuery)
        } else if (searchQuery.isEmpty()) {
            viewModel.searchPages("")
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Поиск
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Поиск страниц...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Поиск") },
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Список страниц
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (errorMessage != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
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
        } else if (pages.isEmpty() && searchQuery.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Страницы не найдены")
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pages) { page ->
                    PageItem(
                        page = page,
                        onClick = { onPageClick(page.uuid) }
                    )
                }
            }
        }
    }
}

@Composable
fun PageItem(
    page: ru.notaspace.data.models.Page,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = page.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (page.isFavorite == true) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Избранное",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (page.type != "page") {
                Text(
                    text = "Тип: ${page.type}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
