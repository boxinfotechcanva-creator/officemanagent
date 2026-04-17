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
fun OrdersScreen() {
    val ctx = LocalContext.current
    val repos = remember { Repos.of(ctx) }
    val list by repos.orders.all().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Orders", style = MaterialTheme.typography.titleLarge)
            Button(onClick = {
                scope.launch {
                    repos.orders.insert(Order(customerName="New Customer", amount=9999.0, status="New", createdAt=System.currentTimeMillis()))
                }
            }) {
                Text("Add")
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list) { o ->
                ElevatedCard(Modifier.fillParentMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(o.customerName, style = MaterialTheme.typography.titleLarge)
                        Text("₹ " + String.format("%.2f", o.amount))
                        Text("Status: " + o.status)
                    }
                }
            }
        }
    }
}