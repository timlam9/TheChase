package com.lamti.thechase.data.websocket

data class ChaseState(
    val board: List<ChaseBox> = emptyList(),
    val gameStatus: GameStatus = GameStatus.SETUP,
    val currentQuestion: GameQuestion = GameQuestion(
        id = "",
        title = "",
        options = emptyList(),
        showRightAnswer = false,
        showPlayerAnswer = false,
        showChaserAnswer = false
    )
)

data class ChaseBox(
    val position: Int,
    val type: RowType,
) {

    enum class RowType(val title: String) {

        CHASER(""),
        CHASER_HEAD("Chaser"),
        PLAYER(""),
        PLAYER_HEAD("Player"),
        EMPTY(""),
        BANK("")
    }
}

data class GameQuestion(
    val id: String,
    val title: String,
    val options: List<GameQuestionOption>,
    val showRightAnswer: Boolean,
    val showPlayerAnswer: Boolean,
    val showChaserAnswer: Boolean,
)

data class GameQuestionOption(
    val title: String,
    val position: Position,
    val selectedBy: SelectedBy,
    val isRightAnswer: Boolean
) {

    enum class Position {

        A,
        B,
        C
    }

    enum class SelectedBy {

        CHASER,
        PLAYER,
        BOTH,
        NONE
    }
}

enum class GameStatus {

    CHASER_WIN,
    PLAYER_WIN,
    PLAYING,
    SETUP
}
