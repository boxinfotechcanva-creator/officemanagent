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
fun InvoicesScreen() {
    val ctx = LocalContext.current
    val repos = remember { Repos.of(ctx) }
    val list by repos.invoices.all().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Invoices", style = MaterialTheme.typography.titleLarge)
            Button(onClick = {
                scope.launch {
                    // attach to first order if exists
                    val o = repos.orders.all().collect {}
                }
            }) {
                Text("Add (create from order)")
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list) { i ->
                ElevatedCard(Modifier.fillParentMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(i.invoiceNumber, style = MaterialTheme.typography.titleLarge)
                        Text("Amount: ₹ " + String.format("%.2f", i.amount))
                        Text("Paid: " + if (i.paid) "Yes" else "No")
                    }
                }
            }
        }
    }
}