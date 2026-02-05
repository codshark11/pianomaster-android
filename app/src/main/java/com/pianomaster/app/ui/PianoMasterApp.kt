package com.pianomaster.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pianomaster.app.navigation.PianoMasterNavGraph
import com.pianomaster.app.navigation.Route
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

private data class NavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun PianoMasterApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val hideNavRail = currentDestination?.route?.let { r ->
        r.startsWith("trainer/") || r.startsWith("course/") || r.startsWith("lesson/")
    } == true

    val navItems = listOf(
        NavItem(Route.Library.route, "Library", Icons.Filled.Home),
        NavItem(Route.Courses.route, "Courses", Icons.Outlined.MenuBook),
        NavItem(Route.Store.route, "Store", Icons.Filled.ShoppingBag),
        NavItem(Route.Settings.route, "Settings", Icons.Filled.Settings),
        NavItem(Route.Profile.route, "Profile", Icons.Filled.Person)
    )

    val railWidth = 80.dp

    Box(modifier = Modifier.fillMaxSize()) {
        PianoMasterNavGraph(
            navController = navController,
            startDestination = Route.Library.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = if (hideNavRail) 0.dp else railWidth)
        )
        if (!hideNavRail) {
            NavigationRail(
                modifier = Modifier.align(Alignment.CenterStart).width(railWidth),
                containerColor = Background,
                contentColor = TextPrimary
            ) {
                navItems.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                    NavigationRailItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label, color = if (selected) Primary else TextSecondary) },
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}
