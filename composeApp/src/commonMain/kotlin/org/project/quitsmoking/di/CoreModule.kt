package org.project.quitsmoking.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.project.quitsmoking.createDataStore
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val coreModule = module {
    singleOf(::createDataStore)
    single<Clock> { Clock.System }
}