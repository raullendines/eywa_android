package com.example.eywa_android.ClassObject

import java.io.Serializable
import java.util.*

class User(val id: Int, var username: String, var password: String, var image: String,
           var quizAchievementList: MutableList<QuizAchievement>, val dateOfRegister: String) : Serializable