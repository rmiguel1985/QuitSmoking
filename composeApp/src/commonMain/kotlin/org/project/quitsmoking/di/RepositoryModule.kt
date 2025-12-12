package org.project.quitsmoking.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.project.quitsmoking.features.health.data.datasource.HealthDataSource
import org.project.quitsmoking.features.health.data.datasource.IHealthDataSource
import org.project.quitsmoking.features.health.data.repository.HealthRepository
import org.project.quitsmoking.features.health.data.repository.IHealthRepository

import org.project.quitsmoking.features.settings.data.datasource.ISettingsDiskDataSource
import org.project.quitsmoking.features.settings.data.datasource.SettingsDiskDataSource
import org.project.quitsmoking.features.settings.data.repository.ISettingsRepository
import org.project.quitsmoking.features.settings.data.repository.SettingsRepository

val repositoryModule = module {
    singleOf(::SettingsDiskDataSource) { bind<ISettingsDiskDataSource>() }
    singleOf(::SettingsRepository) { bind<ISettingsRepository>() }

    singleOf(::HealthDataSource) { bind<IHealthDataSource>() }
    singleOf(::HealthRepository) { bind<IHealthRepository>() }
}