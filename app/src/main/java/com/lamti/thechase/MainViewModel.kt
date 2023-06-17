package com.lamti.thechase

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun onHostClick() {
        _state.update {
            it.copy(
                screen = Screen.HOST_SCREEN,
                user = User.HOST
            )
        }
    }

    fun onPlayerClick() {
        _state.update {
            it.copy(
                screen = Screen.ANSWER_SCREEN,
                user = User.PLAYER
            )
        }
    }

    fun onChaserClick() {
        _state.update {
            it.copy(
                screen = Screen.ANSWER_SCREEN,
                user = User.CHASER
            )
        }
    }

    fun onAnswerClick(answer: String) {
        _state.update {
            it.copy(answer = answer)
        }
    }
}

data class State(
    val screen: Screen = Screen.HOME_SCREEN,
    val user: User = User.NONE,
    val answer: String = ""
)

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