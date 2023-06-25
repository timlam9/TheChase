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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lamti.thechase.ui.theme.ChaseDarkGray

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnswerButton(
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    padding: Dp = 50.dp,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit
) {
    val activeModifier = if (isActive) {
        modifier
            .padding(vertical = padding)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    } else {
        modifier
            .padding(vertical = padding)
            .clip(RoundedCornerShape(10.dp))
            .background(ChaseDarkGray)
    }

    Box(
        modifier = activeModifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            ),
            color = Color.White
        )
    }
}
