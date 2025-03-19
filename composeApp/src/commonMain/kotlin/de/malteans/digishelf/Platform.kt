package de.malteans.digishelf

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform