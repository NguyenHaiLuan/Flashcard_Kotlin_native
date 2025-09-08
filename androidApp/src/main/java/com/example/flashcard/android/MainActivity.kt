package com.example.flashcard.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcard.FlashcardDatabase
import com.example.flashcard.android.navigation.NavGraph
import com.example.flashcard.db.DatabaseDriverFactory
import com.example.flashcard.db.DatabaseModule
import com.example.flashcard.viewmodel.FlashcardViewModel
import java.sql.Driver

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbModule = DatabaseModule(this)
        val viewModel = FlashcardViewModel(dbModule.repository)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(viewModel = viewModel)
                }
            }
        }
    }
}
