package com.boxinfotech.manager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.boxinfotech.manager.data.*

@Composable
fun ProjectDetailScreen(projectId: Long) {
    val ctx = LocalContext.current
    val repos = remember { Repos.of(ctx) }
    val project by repos.projects.byId(projectId).collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    if (project==null) {
        Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Project not found")
        }
        return
    }

    var status by remember(project) { mutableStateOf(project!!.status) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(project!!.name, style = MaterialTheme.typography.titleLarge)
        Text(project!!.description)
        ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
            // Simple status text field
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                scope.launch {
                    repos.projects.update(project!!.copy(status=status))
                }
            }) { Text("Save Status") }
        }
    }
}