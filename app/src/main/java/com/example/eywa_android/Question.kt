package com.example.eywa_android

data class Question
    (
        val id: Int,
        val category: String,
        val difficulty: Int,
        val question: String,
        val correct_answer: String,
        val incorrect_answers: Array<String>
    )
