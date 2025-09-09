package com.example.flashcard.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flashcard.android.screen.AddFlashcardScreen
import com.example.flashcard.android.screen.FlashcardDetailScreen
import com.example.flashcard.android.screen.HomeScreen
import com.example.flashcard.viewmodel.FlashcardViewModel

@Composable
fun NavGraph(viewModel: FlashcardViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToAdd = { navController.navigate("add") },
                onNavigateToDetail = { id -> navController.navigate("detail/$id") }
            )
        }
        composable("add") {
            AddFlashcardScreen(
                onBack = { navController.popBackStack() },
                onAddFlashcard = { question, answer ->
                    viewModel.addFlashcard(question, answer, false)
                    navController.popBackStack()
                }
            )
        }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: return@composable
            FlashcardDetailScreen(
                id = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToDetail = { nextId ->
                    navController.navigate("detail/$nextId") {
                        popUpTo("detail/$id") { inclusive = true }
                    }
                }
            )
        }
    }
}