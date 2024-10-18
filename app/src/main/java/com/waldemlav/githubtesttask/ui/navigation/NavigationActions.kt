package com.waldemlav.githubtesttask.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import com.waldemlav.githubtesttask.R

sealed interface Route {
    @Serializable data object Search : Route
    @Serializable data object History : Route
}

data class TopLevelDestination(
    val route: Route,
    val icon: ImageVector,
    val labelTextId: Int
)

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = Route.Search,
        icon = Icons.Filled.Search,
        labelTextId = R.string.search
    ),
    TopLevelDestination(
        route = Route.History,
        icon = Icons.AutoMirrored.Filled.List,
        labelTextId = R.string.history
    )
)