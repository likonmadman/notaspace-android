package ru.notaspace.ui.views.page.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.notaspace.data.models.PageBlock

/**
 * Компонент для редактирования блока
 */
@Composable
fun BlockEditor(
    block: PageBlock,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var content by remember(block.content) { mutableStateOf(block.content?.toString() ?: "") }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        when (block.type) {
            "heading" -> {
                BasicTextField(
                    value = content,
                    onValueChange = { 
                        content = it
                        onContentChange(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    enabled = enabled
                )
            }
            "quote" -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                        .padding(16.dp)
                ) {
                    BasicTextField(
                        value = content,
                        onValueChange = { 
                            content = it
                            onContentChange(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        ),
                        enabled = enabled
                    )
                }
            }
            "check", "checked" -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = block.type == "checked",
                        onCheckedChange = { /* TODO: обновить тип блока */ },
                        enabled = enabled
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    BasicTextField(
                        value = content,
                        onValueChange = { 
                            content = it
                            onContentChange(it)
                        },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(fontSize = 16.sp),
                        enabled = enabled
                    )
                }
            }
            else -> {
                BasicTextField(
                    value = content,
                    onValueChange = { 
                        content = it
                        onContentChange(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textStyle = TextStyle(fontSize = 16.sp),
                    enabled = enabled
                )
            }
        }
    }
}






