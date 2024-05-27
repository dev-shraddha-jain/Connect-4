package com.groot.connect4.di

import androidx.annotation.ColorRes
import com.groot.connect4.R

object Opponent {

    val OPPONENT_AI = "OPPONENT_AI"
    val OPPONENT_PLAYER = "OPPONENT_PLAYER"
    val OPPONENT_SELF = "OPPONENT_SELF"
}

object Player {
    val player1 = 1 //you
    val player2 = 2 //
}

data class GameConfig(
    val isComputer: Boolean = false,
    val firstTurn: String = Opponent.OPPONENT_SELF,
    @ColorRes val player1Color: Int = R.color.red
)