package com.groot.connect4.utils

import androidx.compose.runtime.MutableState
import com.groot.connect4.ui.screen.dropColor

class ComputerPlayer(private val board: MutableState<Array<Array<Int>>>, private val currentPlayer: Int) {
    fun makeMove() {
        val bestMove = findBestMove(board.value, currentPlayer)
        dropColor(board, bestMove, currentPlayer)
    }

    private fun findBestMove(board: Array<Array<Int>>, player: Int): Int {
        var bestScore = Int.MIN_VALUE
        var bestMove = -1

        for (i in 0 until 7) {
            if (board[0][i] == 0) {
                val newBoard = board.map { it.copyOf() }.toTypedArray()
                dropColorOnBoard(newBoard, i, player)
                val score = minimax(newBoard, 4, false, player)
                if (score > bestScore) {
                    bestScore = score
                    bestMove = i
                }
            }
        }

        return bestMove
    }

    private fun minimax(board: Array<Array<Int>>, depth: Int, isMaximizing: Boolean, player: Int): Int {
        val outcome = checkWin(board)
        if (outcome != BoardLogic.Outcome.NOTHING) {
            return when (outcome) {
                BoardLogic.Outcome.PLAYER1_WINS -> if (player == 1) 100 else -100
                BoardLogic.Outcome.PLAYER2_WINS -> if (player == 2) 100 else -100
                BoardLogic.Outcome.DRAW -> 0
                else -> 0
            }
        }

        if (depth == 0) {
            return 0
        }

        return if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (i in 0 until 7) {
                if (board[0][i] == 0) {
                    val newBoard = board.map { it.copyOf() }.toTypedArray()
                    dropColorOnBoard(newBoard, i, player)
                    val score = minimax(newBoard, depth - 1, false, player)
                    bestScore = maxOf(bestScore, score)
                }
            }
            bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (i in 0 until 7) {
                if (board[0][i] == 0) {
                    val newBoard = board.map { it.copyOf() }.toTypedArray()
                    dropColorOnBoard(newBoard, i, 3 - player)
                    val score = minimax(newBoard, depth - 1, true, player)
                    bestScore = minOf(bestScore, score)
                }
            }
            bestScore
        }
    }

    private fun dropColorOnBoard(board: Array<Array<Int>>, column: Int, player: Int) {
        for (i in board.size - 1 downTo 0) {
            if (board[i][column] == 0) {
                board[i][column] = player
                break
            }
        }
    }

    private fun checkWin(board: Array<Array<Int>>): BoardLogic.Outcome {
        return BoardLogic.checkWin(board)
    }
}