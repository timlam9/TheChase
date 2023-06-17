package com.lamti.thechase

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onHostClick() {
        _uiState.update {
            it.copy(
                screen = UiState.Screen.HOST_SCREEN,
                user = UiState.User.HOST
            )
        }
    }

    fun onPlayerClick() {
        _uiState.update {
            it.copy(
                screen = UiState.Screen.ANSWER_SCREEN,
                user = UiState.User.PLAYER
            )
        }
    }

    fun onChaserClick() {
        _uiState.update {
            it.copy(
                screen = UiState.Screen.ANSWER_SCREEN,
                user = UiState.User.CHASER
            )
        }
    }

    fun onAnswerClick(answer: String) {
        _uiState.update {
            it.copy(answer = answer)
        }
    }

    fun onHostActionClick(action: String) {
        _uiState.update {
            it.copy(hostAction = action)
        }
    }

    fun navigateBack() {
        _uiState.update {
            UiState()
        }
    }
}

data class UiState(
    val screen: Screen = Screen.HOME_SCREEN,
    val user: User = User.NONE,
    val answer: String = "",
    val hostAction: String = ""
) {

    enum class Screen {

        HOME_SCREEN,
        HOST_SCREEN,
        ANSWER_SCREEN
    }

    enum class User {

        HOST,
        PLAYER,
        CHASER,
        NONE
    }
}
