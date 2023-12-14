package com.maxim.arithmetictraining.presentation

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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
        interactor.init()
        communication.update(interactor.loadSettings())
    }

    fun setDifficulty(d: Int) {
        interactor.setDifficulty(d)
        communication.update(interactor.loadSettings())
    }

    fun getActualDifficulty(): Int = interactor.getActualDifficulty()

    fun setLength(length: Int) {
        interactor.setLength(length)
    }

    fun enterNumber(number: Int) {
        interactor.enterNumber(number)
    }

    fun start() {
        viewModelScope.launch(dispatcher) {
            interactor.start(communication)
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
        communication.observe(owner, observer)
    }
}