package com.maxim.arithmetictraining.domain

import com.maxim.arithmetictraining.presentation.Communication
import com.maxim.arithmetictraining.presentation.UiState

interface Interactor {
    fun loadSettings(): UiState
    fun setDifficulty(d: Int)
    fun setLength(length: Int)
    fun enterNumber(number: Int)
    suspend fun start(communication: Communication)
}