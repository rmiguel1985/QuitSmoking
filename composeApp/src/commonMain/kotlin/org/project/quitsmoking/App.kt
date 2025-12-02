package org.project.quitsmoking

import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.project.quitsmoking.ui.navigation.AdaptiveNavigation
import org.project.quitsmoking.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        AdaptiveNavigation()
    }
}