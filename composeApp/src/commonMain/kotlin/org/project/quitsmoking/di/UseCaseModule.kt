package org.project.quitsmoking.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.project.quitsmoking.features.settings.domain.ISettingsUseCase
import org.project.quitsmoking.features.settings.domain.SettingsUseCase

val useCaseModule = module {
    singleOf(::SettingsUseCase) { bind<ISettingsUseCase>() }
}