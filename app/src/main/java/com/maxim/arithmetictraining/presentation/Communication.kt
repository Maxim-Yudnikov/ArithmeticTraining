package com.maxim.arithmetictraining.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication {
    fun update(state: UiState)
    fun observe(owner: LifecycleOwner, observer: Observer<UiState>)

    class Base: Communication {
        private val liveData = MutableLiveData<UiState>()
        override fun update(state: UiState) {
            liveData.postValue(state)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
            liveData.observe(owner, observer)
        }
    }
}