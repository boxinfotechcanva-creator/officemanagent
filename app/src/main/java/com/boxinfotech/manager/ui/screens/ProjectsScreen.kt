package com.boxinfotech.manager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.boxinfotech.manager.data.*
import com.boxinfotech.manager.notifications.notifyAssigned

@Composable
fun ProjectsScreen(nav: NavController) {
    val ctx = LocalContext.current
    val repos = remember { Repos.of(ctx) }
    val list by repos.projects.all().collectAsState(initial = emptyList())
    val team by repos.team.all().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Projects", style = MaterialTheme.typography.titleLarge)
            Button(onClick = {
                scope.launch {
                    repos.projects.insert(Project(name="New Project", description="Describe here", status="Planned", startDate=System.currentTimeMillis(), dueDate=System.currentTimeMillis()+86400000L*30))
                }
            }) {
                Text("Add")
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list) { p ->
                ElevatedCard(Modifier.fillMaxWidth().clickable { nav.navigate("projectDetail/" + p.id) }) {
                    Column(Modifier.padding(16.dp)) {
                        Text(p.name, style = MaterialTheme.typography.titleLarge)
                        Text("Status: " + p.status)
                        Spacer(Modifier.height(8.dp))
                        if (team.isNotEmpty()) {
                            var selected by remember { mutableStateOf(team.first()) }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Assign to:")
                                DropdownMenuBox(team, selected, onChange = { selected = it })
                                Button(onClick = {
                                    scope.launch {
                                        repos.assign.insert(Assignment(projectId=p.id, memberId=selected.id, assignedAt=System.currentTimeMillis()))
                                        notifyAssigned(ctx, p.name, selected.name)
                                    }
                                }) { Text("Assign") }
                            }
                        } else Text("No team members yet")
                    }
                }
            }
        }
    }
}

@Composable
private fun DropdownMenuBox(items: List<TeamMember>, selected: TeamMember, onChange: (TeamMember) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            value = selected.name,
            onValueChange = {},
            readOnly = true,
            label = { Text("Member") },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(item.name + " (" + item.role + ")") }, onClick = {
                    onChange(item); expanded = false
                })
            }
        }
    }
}