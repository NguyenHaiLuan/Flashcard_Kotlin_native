package com.example.flashcard

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform