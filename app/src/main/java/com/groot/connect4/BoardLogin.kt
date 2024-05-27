package com.groot.connect4

import android.util.Log

object BoardLogic {
    private var draw = true
    private var cellValue = 0
    private var p = 0
    private var q = 0
    private var winX = 0
    private var winY = 0

    enum class Outcome {
        NOTHING, DRAW, PLAYER1_WINS, PLAYER2_WINS
    }

    fun checkWin(grid: Array<Array<Int>>): Outcome {
        draw = true
        cellValue = 0
        if (horizontalCheck(grid) || verticalCheck(grid) || ascendingDiagonalCheck(grid) || descendingDiagonalCheck(grid)) {
            Log.d("Winner","check passed")

            return if (cellValue == 1) Outcome.PLAYER1_WINS else Outcome.PLAYER2_WINS
        }
        Log.d("Winner","check win cellValue $cellValue")
        return if (draw) Outcome.DRAW else Outcome.NOTHING
    }

    private fun horizontalCheck(grid: Array<Array<Int>>): Boolean {
        for (i in grid.indices) {
            for (j in 0 until grid[0].size - 3) {
                cellValue = grid[i][j]
                if (cellValue == 0) draw = false
                if (cellValue != 0 && grid[i][j + 1] == cellValue && grid[i][j + 2] == cellValue && grid[i][j + 3] == cellValue) {
                    p = i
                    q = j
                    winX = 1
                    winY = 0

                    Log.d("Winner","horizontalCheck passed")

                    return true
                }
            }
        }
        return false
    }

    private fun verticalCheck(grid: Array<Array<Int>>): Boolean {
        for (j in grid[0].indices) {
            for (i in 0 until grid.size - 3) {
                cellValue = grid[i][j]
                if (cellValue == 0) draw = false
                if (cellValue != 0 && grid[i + 1][j] == cellValue && grid[i + 2][j] == cellValue && grid[i + 3][j] == cellValue) {
                    p = i
                    q = j
                    winX = 0
                    winY = 1
                    Log.d("Winner","verticalCheck passed")

                    return true
                }
            }
        }
        return false
    }

    private fun ascendingDiagonalCheck(grid: Array<Array<Int>>): Boolean {
        for (i in 3 until grid.size) {
            for (j in 0 until grid[0].size - 3) {
                cellValue = grid[i][j]
                if (cellValue == 0) draw = false
                if (cellValue != 0 && grid[i - 1][j + 1] == cellValue && grid[i - 2][j + 2] == cellValue && grid[i - 3][j + 3] == cellValue) {
                    p = i
                    q = j
                    winX = 1
                    winY = -1
                    Log.d("Winner","ascendingDiagonalCheck passed")

                    return true
                }
            }
        }
        return false
    }

    private fun descendingDiagonalCheck(grid: Array<Array<Int>>): Boolean {
        for (i in 3 until grid.size) {
            for (j in 3 until grid[0].size) {
                cellValue = grid[i][j]
                if (cellValue == 0) draw = false
                if (cellValue != 0 && grid[i - 1][j - 1] == cellValue && grid[i - 2][j - 2] == cellValue && grid[i - 3][j - 3] == cellValue) {
                    p = i
                    q = j
                    winX = -1
                    winY = -1
                    Log.d("Winner","descendingDiagonalCheck passed")
                    return true
                }
            }
        }
        return false
    }
}