package com.example.flashcard.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import com.example.flashcard.FlashcardDatabase
import com.example.flashcard.repository.FlashcardRepository
import com.example.flashcard.repository.FlashcardRepositoryImpl

class DatabaseModule (private val context:Context){
    private val driver: SqlDriver by lazy { DatabaseDriverFactory(context).createDriver() }
    val database: FlashcardDatabase by lazy { FlashcardDatabase(driver) }
    val repository: FlashcardRepository by lazy { FlashcardRepositoryImpl(database) }
}