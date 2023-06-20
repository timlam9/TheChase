package com.lamti.thechase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.thechase.data.Repository
import com.lamti.thechase.data.websocket.GameAction
import com.lamti.thechase.data.websocket.GameQuestionOption
import com.lamti.thechase.data.websocket.SocketMessage
import com.lamti.thechase.data.websocket.WebSocket
import kotlinx.coroutines.async
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
            val email = "host@gmail.com"
            val token = async { repository.login(email, "password") }
            val tokenResult = token.await()
            Log.d("LOGIN", "Token: $tokenResult")

            socket.openConnection(tokenResult, email)

            _uiState.update {
                it.copy(
                    screen = UiState.Screen.HOST_SCREEN,
                    user = UiState.User.HOST
                )
            }
            Log.d("LOGIN", "State: $_uiState")
        }
    }

    fun onPlayerClick() {
        viewModelScope.launch {
            val email = "player@gmail.com"
            val token = async { repository.login(email, "password") }
            val tokenResult = token.await()
            Log.d("LOGIN", "Token: $tokenResult")

            socket.openConnection(tokenResult, email)

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
            val email = "chaser@gmail.com"
            val token = async { repository.login(email, "password") }
            val tokenResult = token.await()
            Log.d("LOGIN", "Token: $tokenResult")

            socket.openConnection(tokenResult, email)

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

    fun onHostActionClick(action: String, questionID: Int? = null) {
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
                    },
                    questionID = questionID
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
