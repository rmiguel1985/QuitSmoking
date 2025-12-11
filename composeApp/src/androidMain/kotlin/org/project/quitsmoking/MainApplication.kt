package org.project.quitsmoking

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.project.quitsmoking.di.initKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            appDeclaration = { androidContext(this@MainApplication) },
        )
    }
}