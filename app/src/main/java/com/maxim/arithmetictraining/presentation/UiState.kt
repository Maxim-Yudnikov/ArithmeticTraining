package com.maxim.arithmetictraining.presentation

interface UiState {
    data class Settings(private val diff: Int, private val length: Int): UiState
    data class StartTimer(private val time: Int): UiState
}