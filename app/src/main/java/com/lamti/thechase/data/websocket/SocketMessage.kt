package com.lamti.thechase.data.websocket

import com.lamti.thechase.data.models.ChaseSoundEvent

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

        data class Event(val type: String = "event", val event: ChaseSoundEvent) : InBound()

        data class State(val type: String = "state", val chaseState: ChaseState) : InBound()
    }
}
