package com.maxim.arithmetictraining

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.maxim.arithmetictraining.domain.Interactor
import com.maxim.arithmetictraining.presentation.Communication
import com.maxim.arithmetictraining.presentation.MainViewModel
import com.maxim.arithmetictraining.presentation.UiState
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class MainVIewModelTest {
    private lateinit var interactor: FakeInteractor
    private lateinit var communication: FakeCommunication
    private lateinit var viewModel: MainViewModel
    @Before
    fun before() {
        interactor = FakeInteractor()
        communication = FakeCommunication()
        viewModel = MainViewModel(interactor, communication, Dispatchers.Unconfined)
    }
    @Test
    fun test_init() {
        viewModel.init()
        assertEquals(listOf(UiState.Settings(0, 1)), communication.list)
    }

    @Test
    fun test_set_difficulty() {
        viewModel.setDifficulty(2)
        assertEquals(listOf(UiState.Settings(2, 1)), communication.list)
    }

    @Test
    fun test_set_length() {
        viewModel.setLength(2)
        assertEquals(listOf(UiState.Settings(0, 2)), communication.list)
    }

    @Test
    fun test_enter_number() {
        viewModel.enterNumber(454)
        assertEquals(listOf(454), interactor.numberList)
    }

    @Test
    fun test_start() {
        viewModel.start()
        assertEquals(listOf(UiState.StartTimer(3), UiState.StartTimer(2), UiState.StartTimer(1)), communication.list)
    }


    private class FakeCommunication: Communication {
        var list = mutableListOf<UiState>()
        override fun update(state: UiState) {
            list.add(state)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
            throw IllegalStateException("not using in tests")
        }
    }

    private class FakeInteractor: Interactor {
        private var difficulty = 0
        private var length = 1
        val numberList = mutableListOf<Int>()
        override fun loadSettings(): UiState {
            return UiState.Settings(difficulty, length)
        }

        override fun setDifficulty(d: Int) {
            difficulty = d
        }

        override fun setLength(length: Int) {
            this.length = length
        }

        override fun enterNumber(number: Int) {
            numberList.add(number)
        }

        override suspend fun start(communication: Communication) {
            communication.update(UiState.StartTimer(3))
            communication.update(UiState.StartTimer(2))
            communication.update(UiState.StartTimer(1))
        }
    }
}