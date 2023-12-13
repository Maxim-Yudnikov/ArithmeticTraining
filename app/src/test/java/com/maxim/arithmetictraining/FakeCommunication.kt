package com.maxim.arithmetictraining

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.maxim.arithmetictraining.presentation.Communication
import com.maxim.arithmetictraining.presentation.UiState
import java.lang.IllegalStateException

class FakeCommunication: Communication {
    var list = mutableListOf<UiState>()
    override fun update(state: UiState) {
        list.add(state)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<UiState>) {
        throw IllegalStateException("not using in tests")
    }
}