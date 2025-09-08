package com.example.flashcard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flashcard.model.Flashcard
import com.example.flashcard.repository.FlashcardRepository

class FlashcardViewModel(
    private val repository: FlashcardRepository
) : ViewModel() {

    private val _flashcards = MutableLiveData<List<Flashcard>>(emptyList())
    val flashcards: LiveData<List<Flashcard>> = _flashcards

    init {
        loadFlashcards()
    }

    private fun loadFlashcards() {
        _flashcards.value = repository.getAllFlashcards()
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