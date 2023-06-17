package com.lamti.thechase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.lamti.thechase.ui.AnswerScreen
import com.lamti.thechase.ui.HomeScreen
import com.lamti.thechase.ui.HostScreen
import com.lamti.thechase.ui.theme.TheChaseTheme

class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheChaseTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    val state by viewModel.uiState.collectAsState()

                    when (state.screen) {
                        UiState.Screen.HOME_SCREEN -> {
                            HomeScreen(
                                onHostClick = viewModel::onHostClick,
                                onPlayerClick = viewModel::onPlayerClick,
                                onChaserClick = viewModel::onChaserClick
                            )
                        }

                        UiState.Screen.HOST_SCREEN -> {
                            HostScreen(viewModel::onHostActionClick)
                        }

                        UiState.Screen.ANSWER_SCREEN -> {
                            AnswerScreen(state.user, viewModel::onAnswerClick)
                        }
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (viewModel.uiState.value.screen != UiState.Screen.HOME_SCREEN) {
            viewModel.navigateBack()
        } else {
            super.onBackPressed()
        }
    }
}