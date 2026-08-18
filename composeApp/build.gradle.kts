import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec
import io.kmpbits.splash.ExitAnimation
import io.kmpbits.splash.SplashColor
import io.kmpbits.splash.SplashLogo
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidApplication)

    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)

    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.kover)

    alias(libs.plugins.kmpSplash)
}

splashScreen {
    backgroundColor = SplashColor.hex("#FBFAED")
    backgroundColorNight = SplashColor.hex("#13140D")
    logo = SplashLogo.resource("splash_logo.png")
    logoDark = SplashLogo.resource("splash_logo_dark.png")
    generateAppIcon = false
    exitAnimation = ExitAnimation.FadeOut(300)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.core.splashscreen)
        }
        commonMain.dependencies {
            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Kotlinx datetime
            implementation(libs.kotlinx.datetime)

            // Logging
            implementation(libs.kermit)

            // Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // DataStore
            implementation(libs.androidx.datastore)
            implementation(libs.androidx.datastore.preferences)

            // Coroutines
            implementation(libs.kotlinx.coroutines.test)

            // Navigation
            implementation(libs.androidx.navigation.compose)

            // Turbine
            implementation(libs.turbine)

            // Material Design 3
            implementation(compose.material3)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3AdaptiveNavigationSuite)
            implementation(libs.material3.adaptive)

            // Splash
            implementation(libs.kmpSplash.runtime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.junit)
            implementation(libs.koin.test)
        }
    }
}

android {
    namespace = "org.project.quitsmoking"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.project.quitsmoking"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

// BuildConfig
buildkonfig {
    packageName = "org.project"

    val localProperties =
        Properties().apply {
            val propsFile = rootProject.file("local.properties")
            if (propsFile.exists()) {
                load(propsFile.inputStream())
            }
        }

    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.BOOLEAN,
            "isDebugBuild",
            localProperties["isDebugBuild"]?.toString(),
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "appVersion",
            localProperties["appVersion"]?.toString(),
        )
    }
}

kover {
    reports {
        filters {
            excludes {
                annotatedBy("androidx.compose.runtime.Composable")
                classes(
                    "org.project.quitsmoking.MainActivity",
                    "org.project.quitsmoking.MainApplication",
                    "*ComposableSingletons*",
                    "*CreateDataStore*",
                    "*Screen*",
                    "*Item*",
                    "*InitKoin*",
                )
                packages(
                    "org.project.quitsmoking.ui",
                    "quitsmoking.composeapp.generated.resources",
                    "org.project.quitsmoking.utils"
                )
            }
        }
    }
}

