package com.lamti.thechase.data.websocket

sealed class SocketMessage {

    sealed class OutBound: SocketMessage() {

        data class Connect(val type: String = "connect", val email: String) : OutBound()

        data class Disconnect(val type: String = "disconnect", val email: String) : OutBound()

        data class PlayerAnswer(
            val type: String = "player_answer",
            val email: String,
            val position: GameQuestionOption.Position
        ) : OutBound()

        data class HostAction(
            val type: String = "host_action",
            val questionID: Int? = null,
            val action: GameAction
        ) : OutBound()
    }

    sealed class InBound: SocketMessage() {

        data class SocketError(val type: String = "socket_error", val errorType: String) : InBound()
    }
}

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
        NONE
    }
}

enum class GameAction {

    START,
    SHOW_PLAYER_ANSWER,
    SHOW_CHASER_ANSWER,
    SHOW_RIGHT_ANSWER,
    MOVE_PLAYER,
    MOVE_CHASER,
    NEXT_QUESTION
}