package org.project.quitsmoking.di

import org.koin.test.check.checkModules
import kotlin.test.Test

class KoinModulesTest {

    @Test
    fun checkAllModules() {
        checkModules {
            listOf(
                coreModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}