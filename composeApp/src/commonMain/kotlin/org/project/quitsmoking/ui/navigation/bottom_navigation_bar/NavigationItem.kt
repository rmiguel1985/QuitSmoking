package org.project.quitsmoking.ui.navigation.bottom_navigation_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import org.project.quitsmoking.utils.IconResource
import org.project.quitsmoking.utils.UiText
import org.project.quitsmoking.ui.navigation.AppDestination
import quitsmoking.composeapp.generated.resources.Res
import quitsmoking.composeapp.generated.resources.tab_about
import quitsmoking.composeapp.generated.resources.tab_health
import quitsmoking.composeapp.generated.resources.tab_overview
import quitsmoking.composeapp.generated.resources.tab_settings

enum class NavigationItem(
    val selectedIcon: IconResource,
    val unSelectedIcon: IconResource,
    val title: UiText,
    val route: AppDestination
) {
    OVERVIEW(
        selectedIcon = IconResource.ImageVector(Icons.AutoMirrored.Filled.List),
        unSelectedIcon = IconResource.ImageVector(Icons.AutoMirrored.Outlined.List),
        title = UiText.StringResource(Res.string.tab_overview),
        route = AppDestination.Overview
    ),
    HEALTH(
        selectedIcon = IconResource.ImageVector(Icons.Filled.Favorite),
        unSelectedIcon = IconResource.ImageVector(Icons.Outlined.Favorite),
        title = UiText.StringResource(Res.string.tab_health),
        route = AppDestination.Health,
    ),
    SETTINGS(
        selectedIcon = IconResource.ImageVector(Icons.Filled.Settings),
        unSelectedIcon = IconResource.ImageVector(Icons.Outlined.Settings),
        title = UiText.StringResource(Res.string.tab_settings),
        route = AppDestination.Settings
    ),
    ABOUT(
        selectedIcon = IconResource.ImageVector(Icons.Filled.Info),
        unSelectedIcon = IconResource.ImageVector(Icons.Outlined.Info),
        title = UiText.StringResource(Res.string.tab_about),
        route = AppDestination.About
    ),
}