package org.project.quitsmoking

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform