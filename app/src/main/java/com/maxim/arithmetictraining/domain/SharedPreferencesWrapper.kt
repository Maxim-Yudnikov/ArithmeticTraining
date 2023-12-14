package com.maxim.arithmetictraining.domain

import android.content.SharedPreferences
import android.util.Log

interface SharedPreferencesWrapper {
    fun getInt(key: String): Int
    fun setInt(key: String, value: Int)

    class Base(private val pref: SharedPreferences): SharedPreferencesWrapper {
        override fun getInt(key: String): Int {
            Log.d("MyLog", "Get by key: $key, result: ${pref.getInt(key, 0)}")
            return pref.getInt(key, 0)
        }

        override fun setInt(key: String, value: Int) {
            Log.d("MyLog", "Set value: $value by key: $key")
            pref.edit().putInt(key, value).apply()
        }
    }
}