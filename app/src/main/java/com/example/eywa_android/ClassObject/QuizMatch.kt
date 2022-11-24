package com.example.eywa_android.ClassObject

import java.io.Serializable

class QuizMatch (val userId : Int, val category : String, val time : Int,
                 val difficulty : Int, val points : String) : Serializable