package com.example.flashcard.viewmodel

import com.example.flashcard.model.Flashcard
import com.example.flashcard.repository.FlashcardRepository

class FlashcardViewModel(
    private val repository: FlashcardRepository
) {
    private val _flashcards = mutableListOf<Flashcard>()
    val flashcards: List<Flashcard> get() = _flashcards.toList()

    init {
        loadFlashcards()
    }

    private fun loadFlashcards() {
        _flashcards.clear()
        _flashcards.addAll(repository.getAllFlashcards())
    }

    fun addFlashcard(question: String, answer: String, isLearned: Boolean) {
        repository.insertFlashcard(question, answer, isLearned)
        loadFlashcards()
    }

    fun deleteFlashcard(id: Long) {
        repository.deleteFlashcard(id)
        loadFlashcards()
    }

    fun updateFlashcard(id: Long, question: String, answer: String, isLearned: Boolean) {
        repository.updateFlashcard(id, question, answer, isLearned)
        loadFlashcards()
    }

    fun getFlashcardById(id: Long): Flashcard? {
        return repository.getFlashcardById(id)
    }
}