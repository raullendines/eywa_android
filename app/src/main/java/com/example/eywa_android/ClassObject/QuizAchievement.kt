package com.example.eywa_android.ClassObject

import com.example.eywa_android.R

class QuizAchievement (id : Int, title : String, description : String, owned : Boolean) {

    val id : Int
    val title : String
    val description : String
    var owned : Boolean
    var image : Int

    init {
        this.id= id
        this.title = title
        this.description = description
        this.owned = owned
        image = if (owned){
            R.drawable.trophy
        } else {
            R.drawable.trophy_black_white
        }
    }
}