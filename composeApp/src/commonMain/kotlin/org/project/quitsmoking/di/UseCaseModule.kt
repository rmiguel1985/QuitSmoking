package org.project.quitsmoking.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.project.quitsmoking.features.health.domain.HealthUseCase
import org.project.quitsmoking.features.health.domain.IHealthUseCase
import org.project.quitsmoking.features.settings.domain.ISettingsUseCase
import org.project.quitsmoking.features.settings.domain.SettingsUseCase
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val useCaseModule = module {
    singleOf(::SettingsUseCase) { bind<ISettingsUseCase>() }
    singleOf(::HealthUseCase) { bind<IHealthUseCase>()  }
}