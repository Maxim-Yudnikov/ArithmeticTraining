package com.maxim.arithmetictraining.core

import android.app.Application
import android.content.Context
import com.maxim.arithmetictraining.domain.Interactor
import com.maxim.arithmetictraining.domain.InteractorRandomizer
import com.maxim.arithmetictraining.domain.SharedPreferencesWrapper
import com.maxim.arithmetictraining.presentation.Communication
import com.maxim.arithmetictraining.presentation.MainViewModel

class App : Application() {
    lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        viewModel = MainViewModel(
            Interactor.Base(
                SharedPreferencesWrapper.Base(
                    getSharedPreferences(
                        "Pref",
                        Context.MODE_PRIVATE
                    )
                ), InteractorRandomizer.Base()
            ),
            Communication.Base()
        )
    }
}