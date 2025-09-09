package com.example.flashcard.android.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddFlashcardScreen(
    onBack: () -> Unit,
    onAddFlashcard: (String, String) -> Unit
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Thêm Flashcard mới", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Từ vựng") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Nghĩa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box (
            Modifier.width(IntrinsicSize.Max)
                .align(Alignment.CenterHorizontally)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { onBack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF990000)
                    )
                ) {
                    Text("Quay lại")
                }
                Button(
                    onClick = {
                        if (question.isNotBlank() && answer.isNotBlank()) {
                            onAddFlashcard(question, answer)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF009900)
                    )
                ) {
                    Text("Thêm")
                }
            }
        }
    }
}