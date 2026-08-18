package org.project.quitsmoking.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.project.quitsmoking.features.overview.ui.OverviewViewModel
import org.project.quitsmoking.features.health.ui.HealthViewModel
import org.project.quitsmoking.features.settings.ui.SettingsViewModel

val viewModelModule = module {
    viewModelOf(::OverviewViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::HealthViewModel)
}