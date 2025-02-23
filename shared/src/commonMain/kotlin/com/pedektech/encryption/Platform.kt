package com.pedektech.encryption

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform