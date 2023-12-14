package com.maxim.arithmetictraining.domain

import android.util.Log
import com.maxim.arithmetictraining.presentation.Communication
import com.maxim.arithmetictraining.presentation.UiState
import kotlinx.coroutines.delay

interface Interactor {
    fun init()
    fun loadSettings(): UiState
    fun setDifficulty(d: Int)
    fun setLength(length: Int)
    fun enterNumber(number: Int)
    fun getActualDifficulty(): Int
    suspend fun start(communication: Communication)

    class Base(
        private val pref: SharedPreferencesWrapper,
        private val randomizer: InteractorRandomizer
    ) : Interactor {
        private var diff = 0
        private var length = 0
        private var number = -1
        override fun init() {
            diff = pref.getInt(DIFF)
            length = pref.getInt(LENGTH)
        }

        override fun loadSettings(): UiState {
            return UiState.Settings(diff, length)
        }

        override fun setDifficulty(d: Int) {
            diff = d
            pref.setInt(DIFF, diff)
        }

        override fun setLength(length: Int) {
            this.length = length
            pref.setInt(LENGTH, length)
        }

        override fun enterNumber(number: Int) {
            this.number = number
        }

        //todo test
        override fun getActualDifficulty() = diff

        override suspend fun start(communication: Communication) {
            val guessedNumber = randomizer.getNumber(length)
            communication.update(UiState.StartTimer(3))
            delay(1000)
            communication.update(UiState.StartTimer(2))
            delay(1000)
            communication.update(UiState.StartTimer(1))
            delay(1000)
            communication.update(UiState.ShowNumber(guessedNumber))
            delay(3000)
            communication.update(UiState.Empty)
            delay(2000)
            communication.update(UiState.ShowInput(randomizer.getAction(diff)))
            val rightNumber = randomizer.getRightNumber()
            number = 0
            delay(10000)
            communication.update(if (number == rightNumber) UiState.ShowSuccess else UiState.ShowFail)
            delay(3000)
            communication.update(UiState.Settings(diff, length))
        }

        companion object {
            private const val DIFF = "DIFF"
            private const val LENGTH = "LENGTH"
        }
    }
}