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
import androidx.compose.foundation.layout.width
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
import com.lamti.thechase.ui.theme.ChaseBlue
import com.lamti.thechase.ui.theme.ChaseDarkGray
import com.lamti.thechase.ui.theme.ChaseGreen
import com.lamti.thechase.ui.theme.ChaseRed
import com.lamti.thechase.ui.theme.TheChaseTheme

@Composable
fun HostScreen(onHostActionClick: (answer: String, question: Int?, timer: Int) -> Unit = { _, _, _ -> }) {
    var questionID by remember { mutableStateOf("null") }
    var timer by remember { mutableStateOf(120) }
    var pauseTimer by remember { mutableStateOf("resume_final_timer") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextBox(modifier = Modifier.weight(1f), text = questionID, label = "question id") {
                questionID = it
            }
            Spacer(modifier = Modifier.width(20.dp))
            TextBox(modifier = Modifier.weight(1f), text = timer.toString(), label = "question id") {
                timer = it.toIntOrNull() ?: 120
            }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                title = "Go to Question $questionID",
                color = ChaseDarkGray,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { onHostActionClick("start", questionID.toIntOrNull(), timer) }
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                title = "Start Final",
                color = ChaseDarkGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
            ) { onHostActionClick("start_final", null, timer) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Reset Timer",
                color = ChaseDarkGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
            ) { onHostActionClick("reset_final_timer", null, timer) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = if (pauseTimer == "pause_final_timer") "Pause Timer" else "Resume Timer",
                color = ChaseDarkGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp
            ) {
                onHostActionClick(pauseTimer, null, timer)
                pauseTimer = if (pauseTimer == "pause_final_timer") "resume_final_timer" else "pause_final_timer"
            }
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                title = "Add Player point",
                color = ChaseBlue,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
                onLongClick = { onHostActionClick("remove_player_final_point", null, timer) },
            ) { onHostActionClick("add_player_final_point", null, timer) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Add Chaser point",
                color = ChaseRed,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
                onLongClick = { onHostActionClick("remove_chaser_final_point", null, timer) }
            ) { onHostActionClick("add_chaser_final_point", null, timer) }
        }
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnswerButton(
                title = "Play Intro",
                color = ChaseDarkGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
            ) { onHostActionClick("play_intro", null, timer) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Change Player",
                color = ChaseDarkGray,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp
            ) { onHostActionClick("change_player", null, timer) }
        }
        Row(
            modifier = Modifier
                .weight(1f),
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
            ) { onHostActionClick("show_player_answer", null, timer) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Show Right Answer",
                color = ChaseGreen,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp
            ) { onHostActionClick("show_right_answer", null, timer) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Show Chaser Answer",
                color = ChaseRed,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp
            ) { onHostActionClick("show_chaser_answer", null, timer) }
        }
        Row(
            modifier = Modifier
                .weight(1f),
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
                onLongClick = { onHostActionClick("move_player_back", null, timer) },
            ) { onHostActionClick("move_player", null, timer) }
            Spacer(modifier = Modifier.size(10.dp))
            AnswerButton(
                title = "Move Chaser",
                color = ChaseRed,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                padding = 10.dp,
                onLongClick = { onHostActionClick("move_chaser_back", null, timer) }
            ) { onHostActionClick("move_chaser", null, timer) }
        }
        AnswerButton(
            title = "Next Question",
            color = ChaseDarkGray,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { onHostActionClick("next_question", null, timer) }
    }
}

@Preview(showBackground = true)
@Composable
fun HostScreenPreview() {
    TheChaseTheme {
        HostScreen()
    }
}
