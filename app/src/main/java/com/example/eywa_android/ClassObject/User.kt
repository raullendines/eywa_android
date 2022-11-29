package com.example.eywa_android.ClassObject

import java.io.Serializable


class User(
    val id: Int,
    var username: String,
    var password: String,
    var image: String,
    var quizAchievementList: MutableList<Int>,
    val dateOfRegister: String) : Serializable

