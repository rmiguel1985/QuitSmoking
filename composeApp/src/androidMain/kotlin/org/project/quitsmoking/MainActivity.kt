package org.project.quitsmoking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kmpbits.splash.SplashActivity

class MainActivity : SplashActivity() {
    override suspend fun isReady(): Boolean {
        return true
    }

    override fun onFinished() {
        setContent { App() }
    }

    override fun onPreCreate() {
        enableEdgeToEdge()
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }*/
}