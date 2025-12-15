package com.example.tictactoegame

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var statusText: TextView
    private lateinit var buttons: Array<Button>
    private lateinit var resetButton: Button

    // 0 = empty, 1 = X, 2 = O
    private var gameState = IntArray(9) { 0 }
    private var currentPlayer = 1
    private var isGameActive = true

    private val winningPositions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.tvStatus)
        resetButton = findViewById(R.id.btnReset)

        buttons = arrayOf(
            findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
            findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
            findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8)
        )

        for (btn in buttons) {
            btn.setOnClickListener(this)
        }

        resetButton.setOnClickListener {
            resetGame()
        }
    }

    override fun onClick(view: View?) {
        val clickedButton = view as Button
        var clickedIndex = 0

        // Find button index
        for (i in buttons.indices) {
            if (buttons[i] == clickedButton) {
                clickedIndex = i
                break
            }
        }

        if (!isGameActive || gameState[clickedIndex] != 0) {
            return
        }

        gameState[clickedIndex] = currentPlayer
        if (currentPlayer == 1) {
            clickedButton.text = "X"
            clickedButton.setTextColor(Color.RED)
            statusText.text = "Turn: O"
        } else {
            clickedButton.text = "O"
            clickedButton.setTextColor(Color.BLUE)
            statusText.text = "Turn: X"
        }

        checkForWin()

        if (isGameActive) {
            currentPlayer = if (currentPlayer == 1) 2 else 1
        }
    }

    private fun checkForWin() {
        var winner = 0

        for (pos in winningPositions) {
            if (gameState[pos[0]] == gameState[pos[1]] &&
                gameState[pos[1]] == gameState[pos[2]] &&
                gameState[pos[0]] != 0) {

                winner = gameState[pos[0]]
                isGameActive = false
            }
        }

        if (winner != 0) {
            val winnerSign = if (winner == 1) "X" else "O"
            statusText.text = "$winnerSign Wins!"
            return
        }

        val isDraw = !gameState.contains(0)
        if (isDraw) {
            statusText.text = "It's a Draw!"
            isGameActive = false
        }
    }

    private fun resetGame() {
        gameState = IntArray(9) { 0 }
        currentPlayer = 1
        isGameActive = true
        statusText.text = "Turn: X"

        for (btn in buttons) {
            btn.text = ""
        }
    }
}