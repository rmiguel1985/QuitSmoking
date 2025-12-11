package org.project.quitsmoking.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

import org.project.quitsmoking.features.settings.data.datasource.ISettingsDiskDataSource
import org.project.quitsmoking.features.settings.data.datasource.SettingsDiskDataSource
import org.project.quitsmoking.features.settings.data.repository.ISettingsRepository
import org.project.quitsmoking.features.settings.data.repository.SettingsRepository

val repositoryModule = module {
    singleOf(::SettingsDiskDataSource) { bind<ISettingsDiskDataSource>() }
    singleOf(::SettingsRepository) { bind<ISettingsRepository>() }
}