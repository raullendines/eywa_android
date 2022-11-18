package com.example.eywa_android.ClassObject

import java.io.Serializable

class User (var username : String, var password : String, var image : String, val gender : String, val age : Int, val quizMatchHistory : MutableList<QuizMatch>?) : Serializable