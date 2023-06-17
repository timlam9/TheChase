package com.lamti.thechase.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.lamti.thechase.ui.components.AnswerButton
import com.lamti.thechase.ui.theme.TheChaseTheme

@Composable
fun HostScreen(
    onHostActionClick: (answer: String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
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
            Text(text = "Choose action: ")
        }
        AnswerButton(title = "Start Game", color = Color.DarkGray, onClick = { onHostActionClick("start") })
        AnswerButton(title = "Show Answer", color = Color.DarkGray, onClick = { onHostActionClick("show_answer") })
        AnswerButton(title = "Update Board", color = Color.DarkGray, onClick = { onHostActionClick("update_board") })
        AnswerButton(title = "Next Question", color = Color.DarkGray, onClick = { onHostActionClick("next_question") })
    }
}

@Preview(showBackground = true)
@Composable
fun HostScreenPreview() {
    TheChaseTheme {
        HostScreen()
    }
}
