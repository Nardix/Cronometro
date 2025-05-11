package com.example.mychrono

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private var isRunning = false
    private var isPaused = false
    private var pauseOffset: Long = 0
    private var count: Int = 0

    private lateinit var chronoText: Chronometer
    private lateinit var startButton: Button
    private lateinit var flagButton: Button
    private lateinit var stopButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button
    private lateinit var flagsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chronoText = findViewById(R.id.chronoText)
        startButton = findViewById(R.id.startButton)
        flagButton = findViewById(R.id.flagButton)
        stopButton = findViewById(R.id.stopButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)
        flagsText = findViewById(R.id.flagsText)
        flagsText.text = ""

        startButton.setOnClickListener {
            if (!isRunning) {
                chronoText.base = SystemClock.elapsedRealtime() - pauseOffset
                chronoText.start()
                isRunning = true
                isPaused = false

                // Aggiorna visibilità
                checkVisibility()
            }
        }

        pauseButton.setOnClickListener {
            if (isRunning) {
                chronoText.stop()
                pauseOffset = SystemClock.elapsedRealtime() - chronoText.base
                isRunning = false
                isPaused = true

                // Aggiorna visibilità
                checkVisibility()
            }
        }

        stopButton.setOnClickListener {
            chronoText.stop()
            chronoText.base = SystemClock.elapsedRealtime()
            pauseOffset = 0
            isRunning = false
            isPaused = false

            // Aggiorna visibilità
            checkVisibility()
        }

        resetButton.setOnClickListener {
            chronoText.base = SystemClock.elapsedRealtime()
            pauseOffset = 0
        }

        flagButton.setOnClickListener {
            count += 1
            val elapsedMillis = SystemClock.elapsedRealtime() - chronoText.base
            val newFlag = "${count}: ${elapsedMillis / 1000}s\n"

            val currentText = flagsText.text.toString()
            val updatedText = if (count > 10) {
                currentText.substringAfter("\n") + newFlag
            } else {
                currentText + newFlag
            }

            flagsText.text = updatedText
        }
    }

    private fun checkVisibility() {
        // Aggiorna la visibilità dei pulsanti in base allo stato del cronometro
        if (isRunning) {
            startButton.visibility = Button.GONE
            flagButton.visibility = Button.VISIBLE
            pauseButton.visibility = Button.VISIBLE
            stopButton.visibility = Button.GONE
            resetButton.visibility = Button.VISIBLE
        } else if (isPaused) {
            startButton.visibility = Button.VISIBLE
            flagButton.visibility = Button.GONE
            pauseButton.visibility = Button.GONE
            stopButton.visibility = Button.VISIBLE
            resetButton.visibility = Button.GONE
        } else {
            startButton.visibility = Button.VISIBLE
            flagButton.visibility = Button.GONE
            pauseButton.visibility = Button.GONE
            stopButton.visibility = Button.GONE
            resetButton.visibility = Button.GONE
        }
    }
}