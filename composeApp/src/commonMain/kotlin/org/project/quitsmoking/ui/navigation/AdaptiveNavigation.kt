package org.project.quitsmoking.ui.navigation

import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.project.quitsmoking.features.about.AboutScreen
import org.project.quitsmoking.features.health.HealthScreen
import org.project.quitsmoking.features.overview.OverviewScreen
import org.project.quitsmoking.features.settings.SettingsScreen
import org.project.quitsmoking.ui.navigation.bottom_navigation_bar.NavigationItem
import org.project.quitsmoking.ui.navigation.bottom_navigation_bar.navigationBar

@Composable
fun AdaptiveNavigation() {

    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val currentNavigationItem by remember(navBackStackEntry) {
        derivedStateOf {
            NavigationItem.entries.find { navigationItem ->
                navBackStackEntry.value.isRouteInHierarchy(navigationItem.route::class)
            }
        }
    }

    val currentWindowAdaptiveInfo = currentWindowAdaptiveInfo()
    val layoutType by remember(currentNavigationItem,currentWindowAdaptiveInfo) {
        derivedStateOf {
            if (currentNavigationItem != null) {
                NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo)
            } else {
                NavigationSuiteType.None
            }
        }
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            navigationBar(
                currentNavigationItem = currentNavigationItem,
                onItemClick = {
                    navController.navigate(it.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                }
            )
        },
        layoutType = layoutType
    ) {
        NavHost(
            navController = navController,
            startDestination = AppDestination.Overview,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = spring()
                ) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = spring()
                ) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = spring()
                ) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = spring()
                ) + fadeOut()
            }
        ) {

            composable<AppDestination.Overview> {
                OverviewScreen()
            }

            composable<AppDestination.Health> {
                HealthScreen()
            }
            composable<AppDestination.Settings> {
                SettingsScreen()
            }
            composable<AppDestination.About> {
                AboutScreen()
            }
        }
    }
}