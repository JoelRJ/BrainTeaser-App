package com.example.algebrabrainteasers

import RecyclerAdapter
import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Adapter
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.floor

class MainActivity : AppCompatActivity(), RecyclerAdapter.CellClickListener {

    lateinit var timer: CountDownTimer
    var isRunning = false
    lateinit var brainTeaser: Teaser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var test = arrayListOf(22, 1, 2, 4)

        // Grid RecyclerView
        optionsList.layoutManager = GridLayoutManager(this,2)
        optionsList.adapter = RecyclerAdapter(arrayListOf(), this, this)

        startButton.setOnClickListener {
            if (!isRunning) {
                resultsView.text = ""
                startTimer()
                brainTeaser = Teaser()
                updateProblem()
            }
        }

    }

    override fun onCellClickListener(selected: Int) {
        if (isRunning) {
            brainTeaser.checkAnswer(selected)
            updateProblem()
        }
    }

    fun updateProblem() {
        scoreView.text = "${brainTeaser.wins} / ${brainTeaser.total}"
        brainTeaser.newProblem()
        problemView.text = brainTeaser.getString()
        optionsList.adapter = RecyclerAdapter(brainTeaser.getAnswerList(),
            this, this)
    }

    fun clearProblem() {
        problemView.text = ""
        optionsList.adapter = RecyclerAdapter(arrayListOf(),
            this, this)
        resultsView.text = "You got ${brainTeaser.wins} right out of ${brainTeaser.total}!"
    }

    // Used to update the text view to the current time
    @SuppressLint("SetTextI18n")
    fun updateText(currentTime: Int) {
        val minutes = floor((currentTime / 1000) / 60.0).toInt()
        val seconds = (currentTime / 1000) % 60

        var secondsString = ""
        if (seconds < 10) {
            secondsString = String.format("%02d",seconds)
        }
        else {
            secondsString = seconds.toString()
        }

        var minutesString = ""
        if (minutes < 10) {
            minutesString = String.format("%02d",minutes)
        }
        else {
            minutesString = minutes.toString()
        }

        timerView.text = "$minutesString:$secondsString"
    }

    // Begin timer from wherever the SeekBar currently is
    // SeekBar is set to automatically update progress as time passes
    private fun startTimer() {
        // TODO: Add timer selection capability
        val timerLength = 10 * 1000

        timer = object : CountDownTimer(timerLength.toLong(), 1000) {

            override fun onTick(currentTime: Long) {
                updateText(currentTime.toInt())
                isRunning = true
            }

            override fun onFinish() {
                pauseTimer()
                isRunning = false
                clearProblem()
            }

        }

        timer.start()
    }

    // Pause the timer (by canceling it) and set button text to start
    private fun pauseTimer() {
        timer.cancel()
    }


}

