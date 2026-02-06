package com.pianomaster.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pianomaster.app.navigation.PianoMasterNavGraph
import com.pianomaster.app.navigation.Route
import com.pianomaster.app.ui.theme.CardBackground
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.PrimaryVariant
import com.pianomaster.app.ui.theme.StarPurple
import com.pianomaster.app.ui.theme.StreakOrange
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary
import com.pianomaster.app.ui.theme.TrophyYellow

private data class NavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val menuPanelWidth = 280.dp
private val menuShape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp)
private val itemShape = RoundedCornerShape(18.dp)

private val menuPanelGradient = Brush.verticalGradient(
    colors = listOf(
        Primary.copy(alpha = 0.12f),
        PrimaryVariant.copy(alpha = 0.06f),
        Color.Transparent
    )
)

private val openButtonGradient = Brush.horizontalGradient(
    colors = listOf(Primary, PrimaryVariant)
)

private val starBadgeGradient = Brush.linearGradient(
    colors = listOf(TrophyYellow, StreakOrange)
)

private val menuPanelBorderBrush = Brush.verticalGradient(
    colors = listOf(
        Primary.copy(alpha = 0.5f),
        PrimaryVariant.copy(alpha = 0.3f),
        TrophyYellow.copy(alpha = 0.2f)
    )
)

@Composable
fun PianoMasterApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val hideNavRail = currentDestination?.route?.let { r ->
        r.startsWith("trainer/") || r.startsWith("course/") || r.startsWith("lesson/")
    } == true

    var navRailExpanded by remember { mutableStateOf(false) }

    val navItems = listOf(
        NavItem(Route.Library.route, "Library", Icons.Filled.Home),
        NavItem(Route.Courses.route, "Courses", Icons.Outlined.MenuBook),
        NavItem(Route.Store.route, "Store", Icons.Filled.ShoppingBag),
        NavItem(Route.Settings.route, "Settings", Icons.Filled.Settings),
        NavItem(Route.Profile.route, "Profile", Icons.Filled.Person)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        PianoMasterNavGraph(
            navController = navController,
            startDestination = Route.Library.route,
            modifier = Modifier.fillMaxSize()
        )

        if (navRailExpanded) {
            if (!hideNavRail) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { navRailExpanded = false }
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxHeight()
                        .width(menuPanelWidth)
                        .shadow(28.dp, menuShape, ambientColor = Primary.copy(alpha = 0.15f), spotColor = StarPurple.copy(alpha = 0.1f))
                        .clip(menuShape)
                        .background(Surface)
                        .background(menuPanelGradient)
                        .border(1.dp, menuPanelBorderBrush, menuShape),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(starBadgeGradient),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "Navigate",
                                color = TextPrimary,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.5).sp
                            )
                        }
                        IconButton(
                            onClick = { navRailExpanded = false },
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(CardBackground),
                        ) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Close menu",
                                tint = TextSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        navItems.forEach { item ->
                            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                            val itemBorderBrush = if (selected) Brush.horizontalGradient(
                                colors = listOf(
                                    Primary.copy(alpha = 0.6f),
                                    TrophyYellow.copy(alpha = 0.4f)
                                )
                            ) else null
                            val itemBgBrush = if (selected) Brush.horizontalGradient(
                                colors = listOf(
                                    Primary.copy(alpha = 0.45f),
                                    PrimaryVariant.copy(alpha = 0.35f),
                                    StarPurple.copy(alpha = 0.25f)
                                )
                            ) else Brush.linearGradient(colors = listOf(CardBackground, CardBackground))
                            Row(
                                modifier = Modifier
                                    .clip(itemShape)
                                    .background(itemBgBrush)
                                    .then(
                                        if (itemBorderBrush != null) Modifier.border(1.dp, itemBorderBrush, itemShape)
                                        else Modifier
                                    )
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        navRailExpanded = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(
                                            if (selected) Color.White.copy(alpha = 0.2f)
                                            else Primary.copy(alpha = 0.15f)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        item.icon,
                                        contentDescription = item.label,
                                        tint = if (selected) TrophyYellow else TextSecondary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Text(
                                    text = item.label,
                                    color = if (selected) TextPrimary else TextSecondary,
                                    fontSize = 17.sp,
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                                )
                                if (selected) {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = TrophyYellow.copy(alpha = 0.9f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .size(54.dp)
                    .shadow(12.dp, RoundedCornerShape(27.dp), ambientColor = Primary.copy(alpha = 0.3f), spotColor = Primary.copy(alpha = 0.25f))
                    .clip(RoundedCornerShape(27.dp))
                    .background(openButtonGradient)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { navRailExpanded = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Open menu",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}
