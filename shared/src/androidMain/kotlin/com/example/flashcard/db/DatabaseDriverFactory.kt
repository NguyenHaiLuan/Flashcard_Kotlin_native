package com.example.flashcard.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.flashcard.FlashcardDatabase

class DatabaseDriverFactory (private val context: Context){
    fun createDriver() : SqlDriver {
        return AndroidSqliteDriver(FlashcardDatabase.Schema, context, "flashcard.db")
    }

}