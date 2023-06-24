package com.lamti.thechase.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnswerButton(
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    padding: Dp = 50.dp,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = padding)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
        )
    }
}
