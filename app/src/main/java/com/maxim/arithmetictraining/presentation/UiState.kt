package com.maxim.arithmetictraining.presentation

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

abstract class UiState {
    open fun apply(
        mainLayout: LinearLayout,
        singleText: TextView,
        lengthEditText: EditText,
        diffTextView: TextView,
        ruleTextView: TextView,
        inputNumberEditText: EditText
    ) {
        apply(mainLayout, singleText)
        apply(mainLayout, singleText, lengthEditText, diffTextView)
        apply(mainLayout, singleText, inputNumberEditText)
        apply(mainLayout, singleText, ruleTextView)
        apply(mainLayout, singleText, ruleTextView, inputNumberEditText)
    }

    open fun apply(
        mainLayout: LinearLayout,
        singleText: TextView,
        lengthEditText: EditText,
        diffTextView: TextView
    ) {
    }

    open fun apply(
        mainLayout: LinearLayout,
        singleText: TextView,
    ) {
    }

    open fun apply(
        mainLayout: LinearLayout,
        singleText: TextView,
        ruleTextView: TextView,
    ) {
    }

    open fun apply(
        mainLayout: LinearLayout,
        singleText: TextView,
        inputNumberEditText: EditText
    ) {
    }

    open fun apply(
        mainLayout: LinearLayout,
        singleText: TextView,
        ruleTextView: TextView,
        inputNumberEditText: EditText
    ) {
    }

    data class Settings(private val diff: Int, private val length: Int) : UiState() {
        override fun apply(
            mainLayout: LinearLayout,
            singleText: TextView,
            lengthEditText: EditText,
            diffTextView: TextView
        ) {
            mainLayout.visibility = View.VISIBLE
            singleText.visibility = View.GONE
            lengthEditText.setText(length.toString())
            diffTextView.text = when (diff) {
                0 -> "Easy"
                1 -> "Medium"
                else -> "Hard"
            }
        }
    }

    data class StartTimer(private val time: Int) : UiState() {
        override fun apply(
            mainLayout: LinearLayout,
            singleText: TextView,
        ) {
            mainLayout.visibility = View.GONE
            singleText.visibility = View.VISIBLE
            singleText.text = time.toString()
            val inputMethodManager = singleText.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(singleText.windowToken, 0)
        }
    }

    data class ShowNumber(private val number: Int) : UiState() {
        override fun apply(
            mainLayout: LinearLayout,
            singleText: TextView,
        ) {
            mainLayout.visibility = View.GONE
            singleText.visibility = View.VISIBLE
            singleText.text = number.toString()
        }
    }

    data class ShowInput(private val rule: String) : UiState() {
        override fun apply(
            mainLayout: LinearLayout,
            singleText: TextView,
            ruleTextView: TextView,
            inputNumberEditText: EditText
        ) {
            mainLayout.visibility = View.GONE
            singleText.visibility = View.GONE
            ruleTextView.visibility = View.VISIBLE
            ruleTextView.text = rule
            inputNumberEditText.visibility = View.VISIBLE
            inputNumberEditText.setText("")
        }
    }

    object ShowFail : UiState() {
        override fun apply(
            mainLayout: LinearLayout,
            singleText: TextView,
        ) {
            mainLayout.visibility = View.GONE
            singleText.visibility = View.VISIBLE
            singleText.text = "Fail"
            val inputMethodManager = singleText.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(singleText.windowToken, 0)
        }
    }

    object ShowSuccess : UiState() {
        override fun apply(
            mainLayout: LinearLayout,
            singleText: TextView,
        ) {
            mainLayout.visibility = View.GONE
            singleText.visibility = View.VISIBLE
            singleText.text = "Right"
            val inputMethodManager = singleText.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(singleText.windowToken, 0)
        }
    }

    object Empty : UiState() {
        override fun apply(
            mainLayout: LinearLayout,
            singleText: TextView,
            ruleTextView: TextView,
            inputNumberEditText: EditText
        ) {
            mainLayout.visibility = View.GONE
            singleText.visibility = View.GONE
            ruleTextView.visibility = View.GONE
            inputNumberEditText.visibility = View.GONE
        }
    }
}