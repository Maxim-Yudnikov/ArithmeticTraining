package com.maxim.arithmetictraining

import com.maxim.arithmetictraining.domain.Interactor
import com.maxim.arithmetictraining.domain.InteractorRandomizer
import com.maxim.arithmetictraining.domain.SharedPreferencesWrapper
import com.maxim.arithmetictraining.presentation.UiState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InteractorTest {
    private lateinit var preferences: FakeSharedPreferencesWrapper
    private lateinit var ramdomizer: FakeInteractorRandomizer
    private lateinit var interactor: Interactor

    @Before
    fun before() {
        preferences = FakeSharedPreferencesWrapper()
        ramdomizer = FakeInteractorRandomizer(456)
        interactor = Interactor.Base(preferences, ramdomizer)
    }

    @Test
    fun test_load_settings() {
        interactor.init()
        val actual = interactor.loadSettings()
        assertEquals(UiState.Settings(0, 2), actual)
    }

    @Test
    fun test_set_difficulty() {
        interactor.init()
        var actual = interactor.loadSettings()
        assertEquals(UiState.Settings(0, 2), actual)
        interactor.setDifficulty(1)
        actual = interactor.loadSettings()
        assertEquals(UiState.Settings(1, 2), actual)
    }

    @Test
    fun test_set_length() {
        interactor.init()
        var actual = interactor.loadSettings()
        assertEquals(UiState.Settings(0, 2), actual)
        interactor.setLength(1)
        actual = interactor.loadSettings()
        assertEquals(UiState.Settings(0, 1), actual)
    }

    @Test
    fun test_start_easy() = runBlocking {
        val communication = FakeCommunication()
        interactor.setDifficulty(0)
        interactor.setLength(2)
        interactor.start(communication)
        assertEquals(
            listOf(
                UiState.StartTimer(3),
                UiState.StartTimer(2),
                UiState.StartTimer(1),
                UiState.ShowNumber(458),
                UiState.Empty,
                UiState.ShowInput("Increase each digit by 1"),
                UiState.ShowFail,
                UiState.Settings(0, 2)
            ), communication.list
        )
    }

    @Test
    fun test_start_normal() = runBlocking {
        val communication = FakeCommunication()
        interactor.setDifficulty(1)
        interactor.setLength(2)
        interactor.start(communication)
        assertEquals(
            listOf(
                UiState.StartTimer(3),
                UiState.StartTimer(2),
                UiState.StartTimer(1),
                UiState.ShowNumber(458),
                UiState.Empty,
                UiState.ShowInput("Decrease each digit by 2"),
                UiState.ShowFail,
                UiState.Settings(1, 2)
            ), communication.list
        )
    }

    @Test
    fun test_start_hard() = runBlocking {
        val communication = FakeCommunication()
        interactor.setDifficulty(2)
        interactor.setLength(2)
        interactor.start(communication)
        assertEquals(
            listOf(
                UiState.StartTimer(3),
                UiState.StartTimer(2),
                UiState.StartTimer(1),
                UiState.ShowNumber(458),
                UiState.Empty,
                UiState.ShowInput("Increase each digit by 7"),
                UiState.ShowFail,
                UiState.Settings(2, 2)
            ), communication.list
        )
    }


    private class FakeInteractorRandomizer(private val value: Int) : InteractorRandomizer {
        override fun getNumber(length: Int): Int = value + length
        override fun getAction(diff: Int): String = if (diff == 0) "Increase each digit by 1"
        else if (diff == 1) "Decrease each digit by 2"
        else "Increase each digit by 7"

        override fun getRightNumber(): Int = 343
    }

    private class FakeSharedPreferencesWrapper : SharedPreferencesWrapper {
        private var diff = 0
        private var length = 2
        override fun getInt(key: String): Int {
            return if (key == "DIFF") diff else length
        }

        override fun setInt(key: String, value: Int) {
            if (key == "DIFF") diff = value else length = value
        }
    }
}