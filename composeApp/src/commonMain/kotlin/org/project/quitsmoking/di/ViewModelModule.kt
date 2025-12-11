package org.project.quitsmoking.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.project.quitsmoking.features.settings.ui.SettingsViewModel

val viewModelModule = module {
    viewModelOf(::SettingsViewModel)
}