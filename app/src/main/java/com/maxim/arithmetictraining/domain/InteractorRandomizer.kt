package com.maxim.arithmetictraining.domain

import android.util.Log
import kotlin.math.pow

interface InteractorRandomizer {
    fun getNumber(length: Int): Int
    fun getAction(diff: Int): String
    fun getRightNumber(): Int

    class Base : InteractorRandomizer {
        private var guessedNumber = -1
        private var saveAction = Int.MAX_VALUE
        override fun getNumber(length: Int): Int {
            var number = 0
            while (number.toString().length < length) {
                number = (0 until 10f.pow(length).toInt()).random()
            }
            guessedNumber = number
            return number
        }

        override fun getAction(diff: Int): String {
            val saveAction: Int
            val action = when (diff) {
                0 -> {
                    saveAction = 1
                    "Increase each digit by 1"
                }

                else -> {
                    val range = if (diff == 1) 2 else 9
                    var random = 0
                    while (random == 0) {
                        random = (range * -1..range).random()
                    }
                    saveAction = random
                    if (random < 0)
                        "Decrease each digit by $random"
                    else
                        "Increase each digit by $random"
                }
            }
            this.saveAction = saveAction
            return action
        }

        override fun getRightNumber(): Int {
            var result = 0
            for (i in 0 until guessedNumber.toString().length) {
                var actualDigit = (guessedNumber % (10f.pow(i + 1)) / 10f.pow(i)).toInt()
                actualDigit += saveAction
                if (actualDigit >= 10) actualDigit -= 10
                else if (actualDigit < 0) actualDigit += 10
                result += actualDigit * 10f.pow(i).toInt()
            }
            Log.d("MyLog", "Guessed: $guessedNumber, result: $result")
            return result
        }
    }
}