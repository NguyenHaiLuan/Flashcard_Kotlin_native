package com.example.flashcard.android.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcard.model.Flashcard
import com.example.flashcard.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardDetailScreen(
    id: Long,
    viewModel: FlashcardViewModel,
    onBack: () -> Unit,
    onNavigateToDetail: (Long) -> Unit
) {
    val flashcards by viewModel.flashcards.observeAsState(emptyList())
    val currentIndex = flashcards.indexOfFirst { it.id == id }
    val flashcard = flashcards.getOrNull(currentIndex)

    if (flashcard == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Flashcard không tồn tại")
            Spacer(Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Quay lại")
            }
        }
        return
    }

    var isFlipped by remember { mutableStateOf(false) }
    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        label = "cardFlip"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Flashcard",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentIndex > 0) {
                    IconButton(
                        onClick = {
                            val prevId = flashcards[currentIndex - 1].id
                            onNavigateToDetail(prevId)
                        },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Từ trước",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    Spacer(Modifier.width(56.dp))
                }
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            this.rotationY = rotationY
                            cameraDistance = 8 * density
                        }
                        .clickable(
                            indication = LocalIndication.current,
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
                if (currentIndex < flashcards.lastIndex) {
                    IconButton(
                        onClick = {
                            val nextId = flashcards[currentIndex + 1].id
                            onNavigateToDetail(nextId)
                        },
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Từ tiếp theo",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                } else {
                    Spacer(Modifier.width(56.dp))
                }
            }
        }
    }
}

@Composable
private fun CardFront(flashcard: Flashcard) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = flashcard.question,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
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
            fontSize = 32.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}