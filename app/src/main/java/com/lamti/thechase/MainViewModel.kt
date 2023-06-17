package com.lamti.thechase

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.thechase.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository = Repository()) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onHostClick() {
        viewModelScope.launch {
            val token = repository.login("host@gmail.com", "password")
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
