package com.lamti.thechase.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.thechase.activity.MainView.UiState
import com.lamti.thechase.ui.components.AnswerButton
import com.lamti.thechase.ui.theme.ChaseBlue
import com.lamti.thechase.ui.theme.ChaseGray
import com.lamti.thechase.ui.theme.ChaseRed
import com.lamti.thechase.ui.theme.TheChaseTheme

@Composable
internal fun AnswerScreen(
    hasAnswered: Boolean = false,
    user: UiState.User = UiState.User.NONE,
    onAnswerClick: (answer: String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (user) {
                    UiState.User.PLAYER -> ChaseBlue
                    UiState.User.CHASER -> ChaseRed
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
            Text(text = user.name, style = MaterialTheme.typography.headlineLarge)
            Text(text = "Choose your answer: ")
        }
        AnswerButton(
            isActive = !hasAnswered,
            title = "A",
            color = ChaseGray,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { onAnswerClick("A") }
        AnswerButton(
            isActive = !hasAnswered,
            title = "B",
            color = ChaseGray,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { onAnswerClick("B") }
        AnswerButton(
            isActive = !hasAnswered,
            title = "C",
            color = ChaseGray,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { onAnswerClick("C") }
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerScreenPreview() {
    TheChaseTheme {
        AnswerScreen()
    }
}
