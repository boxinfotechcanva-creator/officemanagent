package com.boxinfotech.manager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import com.boxinfotech.manager.data.Repos
import androidx.compose.ui.platform.LocalContext

@Composable
fun DashboardScreen() {
    val ctx = LocalContext.current
    val repos = remember { Repos.of(ctx) }
    val orders by repos.orders.all().collectAsState(initial = emptyList())
    val invoices by repos.invoices.all().collectAsState(initial = emptyList())
    val projects by repos.projects.all().collectAsState(initial = emptyList())
    val team by repos.team.all().collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Welcome to Box Infotech Manager", style = MaterialTheme.typography.titleLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Orders", orders.size.toString())
            StatCard("Invoices", invoices.size.toString())
            StatCard("Projects", projects.size.toString())
            StatCard("Team", team.size.toString())
        }
        ElevatedCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Quick Tips")
                Text("• Tap Projects to assign members and update status.")
                Text("• Create an order and then issue an invoice.")
                Text("• Notifications are sent when you assign a project.")
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String) {
    ElevatedCard(Modifier.weight(1f)) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title)
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}