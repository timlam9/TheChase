package com.lamti.thechase.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.thechase.activity.MainView.UiState
import com.lamti.thechase.data.Repository
import com.lamti.thechase.data.models.ChaseSoundEvent
import com.lamti.thechase.data.websocket.ChaseState
import com.lamti.thechase.data.websocket.GameAction
import com.lamti.thechase.data.websocket.GameQuestionOption
import com.lamti.thechase.data.websocket.SocketMessage
import com.lamti.thechase.data.websocket.WebSocket
import com.lamti.thechase.toEmail
import com.lamti.thechase.toPosition
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val repository: Repository = Repository(),
    private val socket: WebSocket = WebSocket()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    val socketSoundEvents: SharedFlow<ChaseSoundEvent> = socket.socketSoundEvents.shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed()
    )

    val chaseState: StateFlow<ChaseState> = socket.chaseState

    init {
        chaseState.onEach { serverState ->
            val playerHasAnswered =
                serverState.currentQuestion.options.firstOrNull { it.selectedBy == GameQuestionOption.SelectedBy.PLAYER } != null
            val chaserHasAnswered =
                serverState.currentQuestion.options.firstOrNull { it.selectedBy == GameQuestionOption.SelectedBy.CHASER } != null
            val bothHaveAnswered =
                serverState.currentQuestion.options.firstOrNull { it.selectedBy == GameQuestionOption.SelectedBy.BOTH } != null

            val shouldResetPlayerAnswer =
                _uiState.value.user == UiState.User.PLAYER && !playerHasAnswered && !bothHaveAnswered
            val shouldResetChaserAnswer =
                _uiState.value.user == UiState.User.CHASER && !chaserHasAnswered && !bothHaveAnswered

            if (shouldResetPlayerAnswer || shouldResetChaserAnswer) {
                _uiState.update { it.copy(answer = "") }
            }

        }.launchIn(viewModelScope)
    }

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
                        "move_player_back" -> GameAction.MOVE_PLAYER_BACK
                        "move_chaser" -> GameAction.MOVE_CHASER
                        "move_chaser_back" -> GameAction.MOVE_CHASER_BACK
                        "change_player" -> GameAction.CHANGE_PLAYER
                        "play_intro" -> GameAction.PLAY_INTRO
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
}
