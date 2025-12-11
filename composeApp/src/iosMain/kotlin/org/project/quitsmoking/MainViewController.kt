package org.project.quitsmoking

import androidx.compose.ui.window.ComposeUIViewController
import org.project.quitsmoking.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }