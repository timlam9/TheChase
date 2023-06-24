package com.lamti.thechase.activity

internal class MainView {

    data class UiState(
        val screen: Screen = Screen.HOME_SCREEN,
        val user: User = User.NONE,
        val answer: String = "",
        val hostAction: String = ""
    ) {

        enum class Screen {

            HOME_SCREEN,
            HOST_SCREEN,
            ANSWER_SCREEN
        }

        enum class User {

            HOST,
            PLAYER,
            CHASER,
            NONE
        }
    }
}