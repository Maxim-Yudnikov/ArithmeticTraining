package com.maxim.arithmetictraining.domain

interface InteractorRandomizer {
    fun getNumber(length: Int): Int
    fun getAction(diff: Int): String
    fun getRightNumber(): Int
}