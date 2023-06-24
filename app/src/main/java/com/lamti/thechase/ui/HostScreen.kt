package com.lamti.thechase.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.thechase.ui.components.AnswerButton
import com.lamti.thechase.ui.components.TextBox
import com.lamti.thechase.ui.theme.TheChaseTheme

@Composable
fun HostScreen(onHostActionClick: (answer: String, question: Int?) -> Unit = { _, _ -> }) {
    var questionID by remember { mutableStateOf("null") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextBox(text = questionID, label = "question id") {
                questionID = it
            }
            Spacer(modifier = Modifier.size(20.dp))
        }
        AnswerButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            title = "Go to Question $questionID",
            color = Color.DarkGray,
            onClick = { onHostActionClick("start", questionID.toIntOrNull()) })
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "Show Player Answer",
                padding = 10.dp,
                color = Color.Blue,
                onClick = { onHostActionClick("show_player_answer", null) }
            )
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "Show Right Answer",
                padding = 10.dp,
                color = Color.Green,
                onClick = { onHostActionClick("show_right_answer", null) }
            )
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "Show Chaser Answer",
                padding = 10.dp,
                color = Color.Red,
                onClick = { onHostActionClick("show_chaser_answer", null) }
            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "Move Player",
                padding = 10.dp,
                color = Color.Blue,
                onLongClick = { onHostActionClick("move_player_back", null) },
                onClick = { onHostActionClick("move_player", null) },
            )
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                title = "Move Chaser",
                padding = 10.dp,
                color = Color.Red,
                onLongClick = { onHostActionClick("move_chaser_back", null) },
                onClick = { onHostActionClick("move_chaser", null) }
            )
        }
        AnswerButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            title = "Next Question",
            color = Color.DarkGray,
            onClick = { onHostActionClick("next_question", null) })
    }
}

@Preview(showBackground = true)
@Composable
fun HostScreenPreview() {
    TheChaseTheme {
        HostScreen()
    }
}
