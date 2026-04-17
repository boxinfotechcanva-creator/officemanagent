package com.boxinfotech.manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.boxinfotech.manager.ui.theme.BoxInfotechTheme
import com.boxinfotech.manager.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxInfotechTheme {
                AppScaffold()
            }
        }
    }
}

@Composable
fun AppScaffold() {
    val nav = rememberNavController()
    val items = listOf(
        "dashboard","orders","invoices","projects","team"
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Box Infotech Manager") })
        },
        bottomBar = {
            NavigationBar {
                val entry by nav.currentBackStackEntryAsState()
                val route = entry?.destination?.route
                NavigationBarItem(selected = route=="dashboard", onClick = { nav.navigate("dashboard") }, label={ Text("Home") }, icon={ Icon(Icons.Default.Home, contentDescription=null) })
                NavigationBarItem(selected = route=="orders", onClick = { nav.navigate("orders") }, label={ Text("Orders") }, icon={ Icon(Icons.Default.List, contentDescription=null) })
                NavigationBarItem(selected = route=="invoices", onClick = { nav.navigate("invoices") }, label={ Text("Invoices") }, icon={ Icon(Icons.Default.ReceiptLong, contentDescription=null) })
                NavigationBarItem(selected = route=="projects", onClick = { nav.navigate("projects") }, label={ Text("Projects") }, icon={ Icon(Icons.Default.Work, contentDescription=null) })
                NavigationBarItem(selected = route=="team", onClick = { nav.navigate("team") }, label={ Text("Team") }, icon={ Icon(Icons.Default.Group, contentDescription=null) })
            }
        }
    ) { inner ->
        NavHost(navController = nav, startDestination = "dashboard", modifier = Modifier.padding(inner)) {
            composable("dashboard") { DashboardScreen() }
            composable("orders") { OrdersScreen() }
            composable("invoices") { InvoicesScreen() }
            composable("projects") { ProjectsScreen(nav) }
            composable("team") { TeamScreen() }
            composable("projectDetail/{id}") { backStack ->
                val id = backStack.arguments?.getString("id")?.toLongOrNull() ?: 0L
                ProjectDetailScreen(projectId = id)
            }
        }
    }
}

// Minimal icons
object Icons {
    object Default {
        @Composable fun Home() = androidx.compose.material.icons.Icons.Default.Home
        @Composable fun List() = androidx.compose.material.icons.Icons.Default.List
        @Composable fun ReceiptLong() = androidx.compose.material.icons.Icons.Default.ReceiptLong
        @Composable fun Work() = androidx.compose.material.icons.Icons.Default.Work
        @Composable fun Group() = androidx.compose.material.icons.Icons.Default.Group
    }
}