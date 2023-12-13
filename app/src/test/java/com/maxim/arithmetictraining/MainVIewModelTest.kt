package com.maxim.arithmetictraining

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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
        assertEquals(listOf(State.Settings(0, 1)), communication)
    }

    @Test
    fun test_set_difficulty() {
        viewModel.setDifficulty(2)
        assertEquals(listOf(State.Settings(2, 1)), communication)
    }

    @Test
    fun test_set_length() {
        viewModel.setLength(2)
        assertEquals(listOf(State.Settings(0, 2)), communication)
    }

    @Test
    fun test_enter_number() {
        viewModel.enterNumber(454)
        assertEquals(listOf(454), interactor.number)
    }

    @Test
    fun test_start() {
        viewModel.start()
        assertEquals(listOf(State.StartTimer(3), State.StartTimer(2), State.StartTimer(1)), communication.list)
    }


    private class FakeCommunication: Communication {
        var list = mutableListOf<State>()
        override fun update(state: State) {
            list.add(state)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<State>) {
            throw IllegalStateException("not using in tests")
        }
    }

    private class FakeInteractor: Interactor {
        private var difficulty = 0
        private var length = 3
        val numberList = mutableListOf<Int>()
        override fun loadSettings(): State {
            return State.Settings(difficulty, length)
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

        override fun start(communication: Communication) {
            communication.update(State.StartTimer(3))
            communication.update(State.StartTimer(2))
            communication.update(State.StartTimer(1))
        }
    }
}