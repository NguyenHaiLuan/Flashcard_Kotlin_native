package com.example.flashcard.repository

import com.example.flashcard.FlashcardDatabase
import com.example.flashcard.model.Flashcard

interface FlashcardRepository {
    fun getAllFlashcards(): List<Flashcard>
    fun getFlashcardById(id: Long): Flashcard?
    fun insertFlashcard(question: String, answer: String, isLearned: Boolean)
    fun deleteFlashcard(id: Long)
    fun updateFlashcard(id: Long, question: String, answer: String, isLearned: Boolean)
}

class FlashcardRepositoryImpl(
    private val db: FlashcardDatabase
) : FlashcardRepository {
    override fun getAllFlashcards(): List<Flashcard> =
        db.flashcardQueries.selectAll().executeAsList().map {
            Flashcard(it.id, it.question, it.answer, it.isLearned != 0L)
        }

    override fun getFlashcardById(id: Long): Flashcard? =
        db.flashcardQueries.findById(id).executeAsOneOrNull()?.let {
            Flashcard(it.id, it.question, it.answer, it.isLearned != 0L)
        }

    override fun insertFlashcard(question: String, answer: String, isLearned: Boolean) {
        db.flashcardQueries.insertFlashcard(question, answer, if (isLearned) 1L else 0L)
    }

    override fun deleteFlashcard(id: Long) {
        db.flashcardQueries.deleteFlashcard(id)
    }

    override fun updateFlashcard(id: Long, question: String, answer: String, isLearned: Boolean) {
        db.flashcardQueries.updateFlashcard(question, answer, if (isLearned) 1L else 0L, id)
    }
}