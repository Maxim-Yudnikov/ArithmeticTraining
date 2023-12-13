package com.maxim.arithmetictraining.presentation

import com.maxim.arithmetictraining.domain.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(
    private val interactor: Interactor,
    private val communication: Communication,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    fun init() {
        communication.update(interactor.loadSettings())
    }

    fun setDifficulty(d: Int) {
        interactor.setDifficulty(d)
        communication.update(interactor.loadSettings())
    }

    fun setLength(length: Int) {
        interactor.setLength(length)
        communication.update(interactor.loadSettings())
    }

    fun enterNumber(number: Int) {
        interactor.enterNumber(number)
    }

    fun start() {
        viewModelScope.launch(dispatcher) {
            interactor.start(communication)
        }
    }
}