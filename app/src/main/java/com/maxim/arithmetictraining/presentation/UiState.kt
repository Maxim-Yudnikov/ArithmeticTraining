package com.maxim.arithmetictraining.presentation

interface UiState {
    data class Settings(private val diff: Int, private val length: Int): UiState
    data class StartTimer(private val time: Int): UiState
    data class ShowNumber(private val number: Int): UiState
    data class ShowInput(private val text: String): UiState
    object ShowFail: UiState
    object ShowSuccess: UiState
    object Empty: UiState
}