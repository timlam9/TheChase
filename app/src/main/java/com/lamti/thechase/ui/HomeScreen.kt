package com.lamti.thechase.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .padding(40.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "The Chase", style = MaterialTheme.typography.headlineLarge)
            Text(text = "What are u?")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onHostClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
        ) {
            Text(text = "Host", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(20.dp))
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onPlayerClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White)
        ) {
            Text(text = "Player", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(20.dp))

        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onChaserClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White)
        ) {
            Text(text = "Chaser", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheChaseTheme {
        HomeScreen()
    }
}