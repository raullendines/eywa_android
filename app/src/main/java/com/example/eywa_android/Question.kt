package com.example.eywa_android

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Question
    (
        val id: String,
        val question: String,
        val category: String,
        val difficulty: String,
        val correct_answer: String,
        val incorrect_answers: Array<String>
    ) : Parcelable
