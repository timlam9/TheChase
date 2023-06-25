package com.lamti.thechase.ui.screens

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.thechase.ui.components.HomeButton
import com.lamti.thechase.ui.theme.ChaseBlue
import com.lamti.thechase.ui.theme.ChaseDarkGray
import com.lamti.thechase.ui.theme.ChaseRed
import com.lamti.thechase.ui.theme.TheChaseTheme

@Composable
fun HomeScreen(
    onHostClick: () -> Unit = {},
    onPlayerClick: () -> Unit = {},
    onChaserClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            Text(text = "Who are u?")
        }
        HomeButton(title = "Host", color = ChaseDarkGray, onClick = onHostClick)
        HomeButton(title = "Player", color = ChaseBlue, onClick = onPlayerClick)
        HomeButton(title = "Chaser", color = ChaseRed, onClick = onChaserClick)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TheChaseTheme {
        HomeScreen()
    }
}
