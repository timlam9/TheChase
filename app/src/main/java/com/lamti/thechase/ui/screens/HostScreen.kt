package com.lamti.thechase.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.thechase.ui.components.AnswerButton
import com.lamti.thechase.ui.components.TextBox
import com.lamti.thechase.ui.theme.ChaseBlue
import com.lamti.thechase.ui.theme.ChaseDarkGray
import com.lamti.thechase.ui.theme.ChaseGray
import com.lamti.thechase.ui.theme.ChaseGreen
import com.lamti.thechase.ui.theme.ChaseRed
import com.lamti.thechase.ui.theme.TheChaseTheme

@Composable
fun HostScreen(onHostActionClick: (answer: String, question: Int?) -> Unit = { _, _ -> }) {
    var questionID by remember { mutableStateOf("null") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChaseDarkGray)
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
            title = "Go to Question $questionID",
            color = ChaseGray,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { onHostActionClick("start", questionID.toIntOrNull()) }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                title = "Play Intro",
                color = ChaseGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
            ) { onHostActionClick("play_intro", null) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Change Player",
                color = ChaseGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp
            ) { onHostActionClick("change_player", null) }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                title = "Show Player Answer",
                color = ChaseBlue,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp
            ) { onHostActionClick("show_player_answer", null) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Show Right Answer",
                color = ChaseGreen,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp
            ) { onHostActionClick("show_right_answer", null) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Show Chaser Answer",
                color = ChaseRed,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp
            ) { onHostActionClick("show_chaser_answer", null) }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                title = "Move Player",
                color = ChaseBlue,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
                onLongClick = { onHostActionClick("move_player_back", null) },
            ) { onHostActionClick("move_player", null) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Move Chaser",
                color = ChaseRed,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
                onLongClick = { onHostActionClick("move_chaser_back", null) }
            ) { onHostActionClick("move_chaser", null) }
        }
        AnswerButton(
            title = "Next Question",
            color = ChaseGray,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { onHostActionClick("next_question", null) }
    }
}

@Preview(showBackground = true)
@Composable
fun HostScreenPreview() {
    TheChaseTheme {
        HostScreen()
    }
}
