package org.project.quitsmoking.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestination {

    @Serializable
    data object Overview : AppDestination

    @Serializable
    data object Health : AppDestination

    @Serializable
    data object Settings : AppDestination

    @Serializable
    data object About : AppDestination
}
