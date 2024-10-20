package com.waldemlav.githubtesttask.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.waldemlav.githubtesttask.ui.history.HistoryScreen
import com.waldemlav.githubtesttask.ui.history.HistoryScreenState
import com.waldemlav.githubtesttask.ui.history.HistoryViewModel
import com.waldemlav.githubtesttask.ui.navigation.NavigationActions
import com.waldemlav.githubtesttask.ui.navigation.Route
import com.waldemlav.githubtesttask.ui.navigation.TOP_LEVEL_DESTINATIONS
import com.waldemlav.githubtesttask.ui.search.SearchScreen
import com.waldemlav.githubtesttask.ui.search.SearchScreenState
import com.waldemlav.githubtesttask.ui.search.SearchViewModel
import com.waldemlav.githubtesttask.ui.theme.GithubTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navigationActions = remember(navController) {
                NavigationActions(navController)
            }
            GithubTestTaskTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        NavigationBar {
                            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            imageVector = destination.icon,
                                            contentDescription = stringResource(id = destination.labelTextId)
                                        )
                                    },
                                    label = { Text(stringResource(destination.labelTextId)) },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.hasRoute(destination.route::class)
                                    } == true,
                                    onClick = { navigationActions.navigateTo(destination) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Search,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Route.Search> {
                            val viewModel: SearchViewModel = hiltViewModel()
                            val state: SearchScreenState by viewModel.searchScreenState.collectAsState()
                            SearchScreen(state, viewModel::searchUserRepos, viewModel::downloadRepo)
                        }
                        composable<Route.History> {
                            val viewModel: HistoryViewModel = hiltViewModel()
                            val state: HistoryScreenState by viewModel.screenState.collectAsState()
                            HistoryScreen(state)
                        }
                    }
                }
            }
        }
    }
}