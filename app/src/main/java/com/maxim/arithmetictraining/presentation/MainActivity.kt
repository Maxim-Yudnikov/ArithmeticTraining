package com.maxim.arithmetictraining.presentation

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.maxim.arithmetictraining.R
import com.maxim.arithmetictraining.core.App
import com.maxim.arithmetictraining.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = (application as App).viewModel

        viewModel.observe(this) {
            it.apply(
                binding.mainLayout,
                binding.singleTextView,
                binding.lengthEditText,
                binding.diffTextView,
                binding.ruleTextView,
                binding.inputNumberEditText
            )
        }

        binding.startButton.setOnClickListener {
            viewModel.start()
        }

        binding.changeDifficultyButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Choose difficulty")
                .setSingleChoiceItems(
                    arrayOf("Easy", "Medium", "Hard"),
                    viewModel.getActualDifficulty()
                ) { _, i ->
                    viewModel.setDifficulty(i)
                }.setPositiveButton("Done") { _, _ -> }.create().show()
        }

        binding.startButton.isEnabled = if (binding.lengthEditText.text.toString().isEmpty()) false
        else binding.lengthEditText.text.toString().toInt() >= 3

        binding.lengthEditText.addTextChangedListener {
            val length = binding.lengthEditText.text.toString()
            val number = if (length.isEmpty()) 0 else length.toInt()
            viewModel.setLength(number)
            binding.startButton.isEnabled = number >= 3
        }

        viewModel.init()
    }
}