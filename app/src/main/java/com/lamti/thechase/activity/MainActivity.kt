package com.lamti.thechase.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lamti.thechase.AudioPlayer
import com.lamti.thechase.activity.MainView.*
import com.lamti.thechase.activity.MainView.UiState.*
import com.lamti.thechase.activity.MainView.UiState.Screen.*
import com.lamti.thechase.toAudioRes
import com.lamti.thechase.ui.screens.AnswerScreen
import com.lamti.thechase.ui.screens.HomeScreen
import com.lamti.thechase.ui.screens.HostScreen
import com.lamti.thechase.ui.theme.TheChaseTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()
    private lateinit var audioPlayer: AudioPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheChaseTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val lifecycleOwner = LocalLifecycleOwner.current
                    val state by viewModel.uiState.collectAsState()

                    LaunchedEffect(key1 = Unit) {
                        lifecycleOwner.lifecycleScope.launch {
                            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                viewModel.socketSoundEvents.onEach { event ->
                                    Log.d("AUDIO_EVENT_LOG", "Play audio from event: $event")
                                    audioPlayer.playSound(event.toAudioRes())
                                }.launchIn(this)
                            }
                        }
                    }

                    when (state.screen) {
                        HOME_SCREEN -> HomeScreen(
                            onHostClick = viewModel::onHostClick,
                            onPlayerClick = viewModel::onPlayerClick,
                            onChaserClick = viewModel::onChaserClick
                        )

                        HOST_SCREEN -> HostScreen(
                            onHostActionClick = { action, questionID ->
                                viewModel.onHostActionClick(action, questionID)
                            }
                        )

                        ANSWER_SCREEN -> AnswerScreen(state.user, viewModel::onAnswerClick)
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (viewModel.uiState.value.screen != HOME_SCREEN) {
            viewModel.navigateBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        audioPlayer = AudioPlayer(this, this.resources)
    }

    override fun onStop() {
        super.onStop()

        audioPlayer.stopSound()
        audioPlayer.stopMusic()
    }
}