package com.boxinfotech.manager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import com.boxinfotech.manager.data.*

@Composable
fun TeamScreen() {
    val ctx = LocalContext.current
    val repos = remember { Repos.of(ctx) }
    val list by repos.team.all().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Team Members", style = MaterialTheme.typography.titleLarge)
            Button(onClick = {
                scope.launch {
                    repos.team.insert(TeamMember(name="Member " + (list.size+1), email="user"+(list.size+1)+"@example.com", role="Engineer"))
                }
            }) {
                Text("Add")
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list) { m ->
                ElevatedCard(Modifier.fillParentMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(m.name, style = MaterialTheme.typography.titleLarge)
                        Text(m.email)
                        Text(m.role)
                    }
                }
            }
        }
    }
}