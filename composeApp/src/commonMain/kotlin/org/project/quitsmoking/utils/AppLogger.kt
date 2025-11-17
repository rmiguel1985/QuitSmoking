package org.project.quitsmoking.utils

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import org.project.BuildKonfig

object AppLogger {
    
    val isDebugBuild = BuildKonfig.isDebugBuild
    
    val logger = Logger(
        config = StaticConfig(
            logWriterList = if (isDebugBuild) {
                listOf(platformLogWriter())
            } else {
                // Avoid logging in release
                emptyList()
            }
        ),
        tag = "QuitSmoking"
    )

    inline fun d(tag: String = "", throwable: Throwable? = null, message: () -> String) {
        if (isDebugBuild) {
            logger.d(tag = tag, throwable = throwable, message = message)
        }
    }
    
    inline fun i(tag: String = "", throwable: Throwable? = null, message: () -> String) {
        if (isDebugBuild) {
            logger.i(tag = tag, throwable = throwable, message = message)
        }
    }
    
    inline fun e(tag: String = "", throwable: Throwable? = null, message: () -> String) {
        if (isDebugBuild) {
            logger.e(tag = tag, throwable = throwable, message = message)
        }
    }
    
    inline fun w(tag: String = "", throwable: Throwable? = null, message: () -> String) {
        if (isDebugBuild) {
            logger.w(tag = tag, throwable = throwable, message = message)
        }
    }
}