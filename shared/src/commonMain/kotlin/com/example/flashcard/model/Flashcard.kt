package com.example.flashcard.model

data class Flashcard(
    val id: Long,
    val question: String,
    val answer: String,
    val isLearned: Boolean
)