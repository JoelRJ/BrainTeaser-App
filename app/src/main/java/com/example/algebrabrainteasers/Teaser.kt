package com.example.algebrabrainteasers

import kotlin.random.Random

open class Teaser {
    private var first: Int = 0
    private var second: Int = 0
    var answer: Int = 0
        get() {
            when (this.function) {
                "+" -> {
                    field = first + second
                }
                "-" -> {
                    field = first - second
                }
                "x" -> {
                    field = first * second
                }
            }
            return field
        }
    var possibleAnswers = arrayListOf(0, 0, 0, 0)
    var wins: Int = 0
    var total: Int = 0
    var function: String = "+"
    var functionsList = arrayListOf<String>("+", "-", "x")

    init {
        setFunction()

    }

    private fun setFunction() {
        this.function = functionsList[Random.nextInt(0,3)]
    }

    private fun setValues() {
        when (this.function) {
            "+" -> {
                this.first = Random.nextInt(0, 20)
                this.second = Random.nextInt(0, 20)
            }
            "-" -> {
                this.first = Random.nextInt(0, 20)
                this.second = Random.nextInt(0, 20)
            }
            "x" -> {
                this.first = Random.nextInt(0, 10)
                this.second = Random.nextInt(0, 10)
            }
        }
    }

    private fun setAnswerList() {
        // FIXME: This will currently give you duplicates
        possibleAnswers = arrayListOf(this.answer, Random.nextInt(-20, 40),
            Random.nextInt(0, 40), Random.nextInt(0, 40))

        possibleAnswers.shuffle()
    }

    fun newProblem() {
        setFunction()
        setValues()
        setAnswerList()
    }

    fun getString(): String {
        return "${this.first} ${this.function} ${this.second} = "
    }

    fun getAnswerList(): ArrayList<Int> {
        return possibleAnswers
    }

    fun checkAnswer(selected: Int) {
        if (selected == this.answer) {
            this.wins += 1
        }

        this.total += 1
    }

}