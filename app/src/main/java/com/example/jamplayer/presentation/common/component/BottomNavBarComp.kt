package com.example.jamplayer.presentation.common.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jamplayer.utils.BottomNavItem

@Composable
fun BottomBarNavigation(
    navController: NavController,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
) {
    val currentDestination = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier,
        tonalElevation = 4.dp
    ) {
        items.forEach { item ->
            val currentRoute  = currentDestination.value?.destination?.route
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unSelectedIcon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                alwaysShowLabel = true
            )
        }
    }
}
