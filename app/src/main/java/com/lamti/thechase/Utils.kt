package com.lamti.thechase

import com.lamti.thechase.activity.MainView
import com.lamti.thechase.data.models.ChaseSoundEvent
import com.lamti.thechase.data.websocket.GameQuestionOption


internal fun ChaseSoundEvent.toAudioRes(): Int = when(this) {
    ChaseSoundEvent.INTRO -> R.raw.intro
    ChaseSoundEvent.PRE_GAME -> R.raw.pre_game
    ChaseSoundEvent.QUESTION -> R.raw.chase_question_round
    ChaseSoundEvent.QUESTION_COUNTDOWN -> R.raw.five_sec_countdown
    ChaseSoundEvent.CHASER_WINS -> R.raw.chaser_catch
    ChaseSoundEvent.PLAYER_WINS -> R.raw.contestant_win
    ChaseSoundEvent.CHANGE_PLAYER -> R.raw.inbetween_contestants
    ChaseSoundEvent.CHASER_ANSWER -> R.raw.chaser_answer
    ChaseSoundEvent.CHASER_LOCK -> R.raw.chaser_lock_in
    ChaseSoundEvent.CHASER_MOVE -> R.raw.chaser_moves_down_board
    ChaseSoundEvent.PLAYER_ANSWER -> R.raw.contestant_choice
    ChaseSoundEvent.PLAYER_LOCK -> R.raw.contestant_lock_in
    ChaseSoundEvent.PLAYER_MOVE -> R.raw.contestant_moves_down_board
    ChaseSoundEvent.QUESTION_APPEAR -> R.raw.question_appear
    ChaseSoundEvent.CORRECT_ANSWER -> R.raw.correct_answer
}

internal fun MainView.UiState.User.toEmail() = when (this) {
    MainView.UiState.User.HOST -> "host@gmail.com"
    MainView.UiState.User.PLAYER -> "player@gmail.com"
    MainView.UiState.User.CHASER -> "chaser@gmail.com"
    MainView.UiState.User.NONE -> ""
}

internal fun String.toPosition() = when (this) {
    "A" -> GameQuestionOption.Position.A
    "B" -> GameQuestionOption.Position.B
    else -> GameQuestionOption.Position.C
}
