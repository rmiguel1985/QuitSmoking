package org.project.quitsmoking.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.project.quitsmoking.utils.AppLogger

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        AppLogger.d(tag = "Koin", message = {"Initializing Koin"})
        appDeclaration()
        modules(
            coreModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
        AppLogger.d(tag = "Koin", message = {"Koin initialized"})
    }