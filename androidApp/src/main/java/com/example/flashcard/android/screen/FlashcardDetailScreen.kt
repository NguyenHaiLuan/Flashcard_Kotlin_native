package com.example.flashcard.android.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcard.model.Flashcard
import com.example.flashcard.viewmodel.FlashcardViewModel

@Composable
fun FlashcardDetailScreen(
    id: Long,
    viewModel: FlashcardViewModel,
    onBack: () -> Unit
) {
    val flashcard = viewModel.getFlashcardById(id)
    if (flashcard == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Flashcard không tồn tại")
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Quay lại",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onBack() }
            )
        }
        return
    }

    var isFlipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        label = "cardFlip"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer {
                    this.rotationY = rotationY
                    cameraDistance = 8 * density
                }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { isFlipped = !isFlipped }
                .size(width = 300.dp, height = 180.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (rotationY <= 90f) {
                CardFront(flashcard)
            } else {
                CardBack(flashcard)
            }
        }
    }
}

@Composable
private fun CardFront(flashcard: Flashcard) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = flashcard.question,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun CardBack(flashcard: Flashcard) {
    Box(
        modifier = Modifier
            .graphicsLayer { scaleX = -1f }
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = flashcard.answer,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}