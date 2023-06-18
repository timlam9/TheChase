package com.lamti.thechase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.thechase.data.Repository
import com.lamti.thechase.data.websocket.GameAction
import com.lamti.thechase.data.websocket.GameQuestionOption
import com.lamti.thechase.data.websocket.SocketMessage
import com.lamti.thechase.data.websocket.WebSocket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository = Repository(),
    private val socket: WebSocket = WebSocket()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onHostClick() {
        viewModelScope.launch {
            val token = repository.login("host@gmail.com", "password")
            socket.openConnection(token, "host@gmail.com")
            Log.d("LOGIN", "Token: $token")

            _uiState.update {
                it.copy(
                    screen = UiState.Screen.HOST_SCREEN,
                    user = UiState.User.HOST
                )
            }
        }
    }

    fun onPlayerClick() {
        viewModelScope.launch {
            val token = repository.login("player@gmail.com", "password")
            socket.openConnection(token, "player@gmail.com")
            Log.d("LOGIN", "Token: $token")

            _uiState.update {
                it.copy(
                    screen = UiState.Screen.ANSWER_SCREEN,
                    user = UiState.User.PLAYER
                )
            }
        }
    }

    fun onChaserClick() {
        viewModelScope.launch {
            val token = repository.login("chaser@gmail.com", "password")
            socket.openConnection(token, "chaser@gmail.com")
            Log.d("LOGIN", "Token: $token")

            _uiState.update {
                it.copy(
                    screen = UiState.Screen.ANSWER_SCREEN,
                    user = UiState.User.CHASER
                )
            }
        }
    }

    fun onAnswerClick(answer: String) {
        viewModelScope.launch {
            socket.sendMessage(
                SocketMessage.OutBound.PlayerAnswer(
                    email = _uiState.value.user.toEmail(),
                    position = answer.toPosition()
                )
            )
        }

        _uiState.update {
            it.copy(answer = answer)
        }
    }

    fun onHostActionClick(action: String) {
        viewModelScope.launch {
            socket.sendMessage(
                SocketMessage.OutBound.HostAction(
                    action = when (action) {
                        "start" -> GameAction.START
                        "show_player_answer" -> GameAction.SHOW_PLAYER_ANSWER
                        "show_chaser_answer" -> GameAction.SHOW_CHASER_ANSWER
                        "show_right_answer" -> GameAction.SHOW_RIGHT_ANSWER
                        "move_player" -> GameAction.MOVE_PLAYER
                        "move_chaser" -> GameAction.MOVE_CHASER
                        else -> GameAction.NEXT_QUESTION
                    }
                )
            )
        }

        _uiState.update {
            it.copy(hostAction = action)
        }
    }

    fun navigateBack() {
        _uiState.update {
            UiState()
        }
        socket.closeWebSocket()
    }

    private fun UiState.User.toEmail() = when (this) {
        UiState.User.HOST -> "host@gmail.com"
        UiState.User.PLAYER -> "player@gmail.com"
        UiState.User.CHASER -> "chaser@gmail.com"
        UiState.User.NONE -> ""
    }

    private fun String.toPosition() = when (this) {
        "A" -> GameQuestionOption.Position.A
        "B" -> GameQuestionOption.Position.B
        else -> GameQuestionOption.Position.C
    }
}
