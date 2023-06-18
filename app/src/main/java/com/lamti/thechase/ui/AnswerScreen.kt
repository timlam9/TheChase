package com.lamti.thechase.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.thechase.UiState
import com.lamti.thechase.ui.components.AnswerButton
import com.lamti.thechase.ui.theme.TheChaseTheme

@Composable
fun AnswerScreen(
    user: UiState.User = UiState.User.NONE,
    onAnswerClick: (answer: String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (user) {
                    UiState.User.PLAYER -> Color.Blue
                    UiState.User.CHASER -> Color.Red
                    else -> MaterialTheme.colorScheme.background
                }
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "The Chase", style = MaterialTheme.typography.headlineLarge)
            Text(text = "Choose your answer: ")
        }
        AnswerButton(modifier = Modifier.fillMaxWidth(), title = "A", color = Color.Black, onClick = { onAnswerClick("A") })
        AnswerButton(modifier = Modifier.fillMaxWidth(), title = "B", color = Color.Black, onClick = { onAnswerClick("B") })
        AnswerButton(modifier = Modifier.fillMaxWidth(), title = "C", color = Color.Black, onClick = { onAnswerClick("C") })
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerScreenPreview() {
    TheChaseTheme {
        AnswerScreen()
    }
}
