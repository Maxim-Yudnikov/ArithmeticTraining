package com.maxim.arithmetictraining.domain

import android.content.SharedPreferences

interface SharedPreferencesWrapper {
    fun getInt(key: String): Int
    fun setInt(key: String, value: Int)

    class Base(private val pref: SharedPreferences): SharedPreferencesWrapper {
        override fun getInt(key: String): Int {
            return pref.getInt(key, 0)
        }

        override fun setInt(key: String, value: Int) {
            pref.edit().putInt(key, value).apply()
        }
    }
}