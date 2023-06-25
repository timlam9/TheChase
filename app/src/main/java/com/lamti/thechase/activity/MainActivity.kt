package com.lamti.thechase.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.lamti.thechase.AudioPlayer
import com.lamti.thechase.R
import com.lamti.thechase.activity.MainView.*
import com.lamti.thechase.activity.MainView.UiState.*
import com.lamti.thechase.activity.MainView.UiState.Screen.*
import com.lamti.thechase.data.models.ChaseSoundEvent
import com.lamti.thechase.data.models.ChaseSoundEvent.CHANGE_PLAYER
import com.lamti.thechase.data.models.ChaseSoundEvent.CHASER_WINS
import com.lamti.thechase.data.models.ChaseSoundEvent.INTRO
import com.lamti.thechase.data.models.ChaseSoundEvent.PLAYER_WINS
import com.lamti.thechase.data.models.ChaseSoundEvent.QUESTION_APPEAR
import com.lamti.thechase.data.models.ChaseSoundEvent.QUESTION_COUNTDOWN
import com.lamti.thechase.data.models.ChaseSoundEvent.STOP_QUESTION_COUNTDOWN
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
        setFullscreen()
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
                                    event.playAudio()
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
                            onHostActionClick = { action, questionID, timer ->
                                viewModel.onHostActionClick(action, questionID, timer)
                            }
                        )

                        ANSWER_SCREEN -> AnswerScreen(
                            hasAnswered = state.answer != "",
                            user = state.user,
                            onAnswerClick = viewModel::onAnswerClick
                        )
                    }
                }
            }
        }
    }

    private fun ChaseSoundEvent.playAudio() {
        when (this) {
            QUESTION_APPEAR -> {
                audioPlayer.playSound(toAudioRes())
                audioPlayer.playBackgroundMusic(R.raw.chase_question_round)
            }

            QUESTION_COUNTDOWN -> audioPlayer.playBackgroundMusic(R.raw.five_sec_countdown)
            STOP_QUESTION_COUNTDOWN -> audioPlayer.stopMusic()
            INTRO -> audioPlayer.playBackgroundMusic(R.raw.intro)
            CHANGE_PLAYER -> audioPlayer.playBackgroundMusic(R.raw.inbetween_contestants)
            CHASER_WINS -> audioPlayer.playBackgroundMusic(R.raw.chaser_catch)
            PLAYER_WINS -> audioPlayer.playBackgroundMusic(R.raw.contestant_win)
            else -> audioPlayer.playSound(toAudioRes())
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

    private fun setFullscreen() {
        val windowInsetsController: WindowInsetsControllerCompat? =
            ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
        WindowCompat.setDecorFitsSystemWindows(window, false)
        hideSystemUI()
    }

    private fun hideSystemUI() {
        //Hides the ugly action bar at the top
        actionBar?.hide()

        //Hide the status bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}
