package org.project.quitsmoking

import androidx.compose.ui.window.ComposeUIViewController
import io.kmpbits.splash.SplashConfig
import org.project.quitsmoking.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    SplashConfig(
        isReady = {
            true
        }
    ) {
        App()
    }
}